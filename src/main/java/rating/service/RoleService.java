package rating.service;

import rating.dao.RoleDAO;
import rating.domain.RoleEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("roleService")
public class RoleService {
    protected static Logger logger = Logger.getLogger("service");
    @Autowired
    private RoleDAO roleDAO;

    public RoleEntity getById(final Integer wantedId) {
//        logger.debug("Retrieving Role "+wantedId);
        if (wantedId < 1) {
            RoleEntity role = new RoleEntity();
            role.setId(0);
            role.setDescription("");
            role.setName("");
            return role;
        }
        return roleDAO.getById(wantedId);
    }

    public List<RoleEntity> getAll() {
        logger.debug("Retrieving All roles ");

        return roleDAO.getAll();
    }

    public List<RoleEntity> getAllForUniFac(int shifrUni, int shifrFac) {
        return roleDAO.getAllForUniFac(shifrUni, shifrFac);
    }

    public List<RoleEntity> getAllForUni() {
        return roleDAO.getAllForUni();
    }


}
