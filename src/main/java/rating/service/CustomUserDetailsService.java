package rating.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rating.dao.UserDAO;
import rating.domain.StatisticEntity;
import rating.domain.UserEntity;
import rating.dto.UserDTO;
import rating.dto.UserDTOService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Service("customUserDetailsService")

//@SessionAttributes({"userEntity", ""})
public class CustomUserDetailsService implements UserDetailsService {

    protected static Logger logger = Logger.getLogger("controller");
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PodrService podrService;
    @Autowired
    StatisticService sService;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired
    private UsersRolesService urService;
    @Autowired
    private RoleService roleService;
    private UserEntity userEntity;
    private UserDTO userDTO;

    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
//        logger.debug("in findByUsername " + username);
        int i = userDAO.getCountByLogin(username);
//        logger.debug("in findByUsername countbyLogin=" + i);
        UserEntity ue;
        if (i==0)
            ue=null;
        else
            ue = userDAO.getByLogin(username);
//        logger.debug("in findByUsername near exit");

        return ue;
    }

    @Override
    //  @Transactional(readOnly = true)
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.debug("Received login UserDetail username- " + username);

        try {

            userEntity = findByUsername(username);
            if (userEntity == null) {
//                logger.debug("Nothing finded");
                throw new UsernameNotFoundException("User is not found");
            }

            userDTO = new UserDTO();
            userDTO.setFIO(userEntity.getName());
            userDTO.setShifrPodr(userEntity.getShifrPodr());
            String podrName;
            if (userDTO.getShifrPodr() > 0) {
                podrName = podrService.getById((int) userDTO.getShifrPodr()).getName().toLowerCase();
                userDTO.setNamePodr(podrName);
            } else {
                userDTO.setNamePodr("Автодор");
            }
            userDTO.setId(userEntity.getId());
            userDTO.setLogin(userEntity.getLogin());
            userDTO.setPassword(userEntity.getPassword());
            userDTO.setTabno(userEntity.getTabno());

            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            WebAuthenticationDetails wad = null;
            String userIPAddress;
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            if (request == null) {
                userIPAddress = "Undefined 0";
            } else {
                userIPAddress = request.getRemoteAddr();
            }
//            logger.debug("userIPAddress=" + userIPAddress);
            // Get the IP address of the user tyring to use the site
            StatisticEntity sEntity = new StatisticEntity();
            sEntity.setIp(userIPAddress);
            sEntity.setShifrwrk((int) userEntity.getId());
            sService.saveRecord(sEntity);
            userEntity.setsEntity(sEntity);
//            request.getSession().setAttribute("userEntity", userEntity);
//            request.getSession().setAttribute("sEntity", sEntity);


            //     auth.getDetails()
            return new UserDTOService(userEntity.getLogin(),
//                    userEntity.getPassword().toLowerCase(),
                    userEntity.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities((int) userEntity.getId()),
                    userDTO);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(userId));
        return authList;
    }

    public List<String> getRoles(int userId) {
        //     List<String> roles = new ArrayList<String>();
        List<String> roles = urService.getAllRoleNamesForUser(userId);
        //   if (roles.size()==0) {
        roles.add("ROLE_USER");
        //   }
//        if (role.intValue() == 1) {
//            roles.add("ROLE_USER");
//            roles.add("ROLE_ADMIN");
//        } else if (role.intValue() == 2) {
//            roles.add("ROLE_USER");
//            roles.add("ROLE_ADMIN");
//        } else if (role.intValue() == 6) {
//            roles.add("ROLE_USER");
//        }
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    public String getFIO() {
        if (userEntity != null) {
            return userEntity.getName();
        } else {
            return "Фамилия не введена";
        }
    }
}
