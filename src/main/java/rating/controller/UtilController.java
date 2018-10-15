package rating.controller;

import net.sf.jasperreports.engine.JRDataSource;
import org.apache.log4j.Logger;
import org.apache.tiles.autotag.core.runtime.annotation.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import rating.domain.*;
import rating.dto.*;
import rating.service.*;
import rating.service.validators.*;
import rating.util.NormalizeString;
import rating.util.ShortFio;
import rating.util.UserInfo;
import rating.util.Util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/util")
public class UtilController {
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private UStepService uStepService;
    @Autowired
    private UsersRolesService urService;
    @Autowired
    private PodrService podrService;
    @Autowired
    private UserService userService;
    @Autowired
    private PodrValidator podrValidator;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PokazService pokazService;
    @Autowired
    private PokazValidator pokazValidator;

    @Autowired
    private DolgService dolgService;
    @Autowired
    private DolgValidator dolgValidator;

    @Autowired
    private NtrService ntrService;
    @Autowired
    private NtrAuthService ntrAuthService;
    @Autowired
    private NtrAuthorsDTOValidator ntrAuthorsDTOValidator;
    @Autowired
    private NtrRecFromJSONDTOValidator ntrRecFromJSONDTOValidator;
    @Autowired
    private NtrPokazService ntrPokazService;
    @Autowired
    private NtrDocService ntrDocService;
    @Autowired
    UploadedNtrDocValidator uploadedNtrDocValidator;
    @Autowired
    private WidBlobService widBlobService;
    @Autowired
    private MailSenderService mailService;

    @Autowired
    private ChgPwdValidator chgPwdValidator;


    @Autowired
    private HttpServletRequest httpServletRequest;
//    private ServletContext servletContext;

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;
//    @Value("#{request.URI}")
//    private String myURL;
    @Secured({"ROLE_USER","ROLE_ADMIN","USER","ADMIN"})
    @RequestMapping(value="/ustep/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  ItemErrorDTO getShortNameUStepByCode(@PathVariable("id") int id) {
        String shortName;
        if (id>0) {
            shortName=uStepService.getById(id).getShortName();
        } else {
            shortName="";
        }
        ItemErrorDTO item=new ItemErrorDTO(id,shortName,"");
        return item;

    }

    @RequestMapping(value="/getfaclist/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> getFacListForUni(@PathVariable("id") int id) {
        String shortName;
//        if (id>0) {
//            shortName=uStepService.getById(id).getShortName();
//        } else {
//            shortName="";
//        }
        List<ItemErrorDTO> facs; 
        facs=podrService.getListDTOForOwner(id);
        return facs;
    }
    @RequestMapping(value="/user",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  UserDTOForVUE getUserInfoForVUE() {
        UserDTOForVUE userDTOForVUE = new UserDTOForVUE();
        List<RoleDTO> roles = new ArrayList<RoleDTO>();
        UserInfo userInfo=new UserInfo();
        String viewName;
        int shifrPodr;
        shifrPodr=(int)userInfo.getUserDTO().getShifrPodr();
        int shifrIdUser;
        shifrIdUser=(int)userInfo.getUserDTO().getId();
        userDTOForVUE.setId(shifrIdUser);
        userDTOForVUE.setShifrPodr(shifrPodr);
        String fio;
        fio= ShortFio.getShortFio((String) userInfo.getUserDTO().getFIO());
        userDTOForVUE.setFIO(fio);
        userDTOForVUE.setNamePodr((String) userInfo.getUserDTO().getNamePodr());
        userDTOForVUE.setRoles(urService.getAllRoleDTOForUser(shifrIdUser));

        return userDTOForVUE;

    }
    @RequestMapping(value="/user/fio/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> getFioNprForFolter(@PathVariable("id") int id) {
        String shortFio;
        StringBuffer fio = new StringBuffer();
        UserEntity uEntity=userService.getById(id);
        fio.append(uEntity.getFam());
        if (uEntity.getNam()!=null && uEntity.getNam().length()>1)
            fio.append(" "+uEntity.getNam());
        if (uEntity.getOtc()!=null && uEntity.getOtc().length()>1)
            fio.append(" "+uEntity.getOtc());
        String f;
        f=fio.toString();
        logger.debug("get Fio Fio="+f);
        shortFio= ShortFio.getShortFio(f);
        logger.debug("get Fio shortFio="+shortFio);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(1, shortFio, shortFio));
        return okList;
    }
    @RequestMapping(value="/univs/{owner}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<PodrEntityDTO> getUnivList(@PathVariable("owner") int owner) {
//        String shortName;
//        if (id>0) {
//            shortName=uStepService.getById(id).getShortName();
//        } else {
//            shortName="";
//        }
        List<PodrEntityDTO> univs;
        logger.debug("Try find podrs for owner = "+owner);

        univs=podrService.getAllForOwnerDTO(owner);
        return univs;
    }
    @RequestMapping(value="/podrsrec/{owner}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  PodrTreeDTO getPodrTreeForOwner(@PathVariable("owner") int owner) {
//        String shortName;
//        if (id>0) {
//            shortName=uStepService.getById(id).getShortName();
//        } else {
//            shortName="";
//        }
        PodrTreeDTO podrs;
        logger.debug("Try find tree for owner = "+owner);

        podrs=podrService.getAllForOwnerDTORecursive(owner);
        return podrs;
    }

    @RequestMapping(value="/univ/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  PodrEntity getUnivName(@PathVariable("id") int id) {
        PodrEntity podrEntity;
        podrEntity=podrService.getById(id);
        return podrEntity;
    }

    @Secured({"ROLE_ADMIN","ADMIN"})

    @RequestMapping(value = "/univ/save", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
//    @PostMapping(path = "/univ/save", consumes = "application/json")
    @ResponseBody
//    public List<ItemErrorDTO> saveUnivEntity(@ModelAttribute("PodrEntity") PodrEntity pEntity, BindingResult result, HttpSession session, Model model) {
    public List<ItemErrorDTO> saveUnivEntity(@RequestBody PodrEntity pEntity, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        int action=2;
        if (pEntity==null) {
            logger.debug("Empty UnivRec row for saving");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Пустая запись"));
            return errList;
        }
        if (pEntity.getId()==0) {
            action=1;
        }
        logger.debug("save UnivRec row for " + pEntity.toString());
//        System.out.println("save UnivRec row for " + pEntity.toString());
        podrValidator.validate(pEntity, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
//        logger.debug("save UnivRec row for " + pEntity.toString());
        if ((pEntity.getId()==0) && (pEntity.getShifrIdOwner()<1)) {
            pEntity.setShifrIdOwner(1);
        }
        podrService.saveRecord(pEntity);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        long v=0;
        if (action==1) v=pEntity.getId();
        okList.add(new ItemErrorDTO((int)v, "Ok", "Ok"));
//        okList.add(new ItemErrorDTO(1, "Ошибка 1", "O1"));
//        okList.add(new ItemErrorDTO(2, "Ошибка 2", "O2"));
//        okList.add(new ItemErrorDTO(3, "Ошибка 3", "O3"));
//        logger.debug("amnt or list record " + okList.size());
        return okList;
    }

    @RequestMapping(value="/univ/del/{id}",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> deleteUniversity(@PathVariable("id") int id) {
        logger.debug("delete Predp id= " + id);
        podrService.deleteRecord(id);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(1, "Ok", "Ok"));
        return okList;
    }

    @RequestMapping(value="/univ/bc/{id}/{finish}",method = RequestMethod.GET)
    @ResponseBody
    public  String getUnivBredCrumb(@PathVariable("id") int owner,@PathVariable("finish") int lastOwner) {
        logger.debug("bread crumb for id= " + owner);
        String retVal;
        PodrEntity podrEntity;  
        boolean done;
        String s;
        done=false;
        StringBuilder retValC=new StringBuilder();
        int currOwnerId=owner;
        int i;
        String s1;
        i=0;
        while (!done) {
       //     logger.debug("i= " + i+" currOwnerId="+currOwnerId);
            podrEntity=podrService.getById(currOwnerId);
            if (podrEntity==null) {
                s="Нет";
            } else {
                s=podrEntity.getShortName().trim();
                if ((s==null) || (s=="")) {
                    s=podrEntity.getName().trim();
                }
            }
            if (i==0) {
               s1="<div class=\"active section\">"+s.trim()+"</div>";
                retValC.append(s1);
            } else {
                s1="<a href=\"/r/mainpage#/univ/"+currOwnerId+"\" class=\"section\">"+s.trim()+"</a>";
                retValC.insert(0,"<div class=\"divider\"> / </div>");
                retValC.insert(0,s1);
            }
            if (currOwnerId==lastOwner) {
                break;
            }
            currOwnerId=(int)podrEntity.getShifrIdOwner();
            i++;
            if (i>3) {
                done=true;
                break;
            }
            if (currOwnerId<1) {
                done=true;
                break;
            }

        }
        retVal=retValC.toString();
    //    logger.debug("bread crumb = " + retVal);
        return retVal;
    }


//    @RequestMapping(value="/users/{shifruni}/{shifrfac}/{shifrkaf}",method = RequestMethod.GET,produces = "application/json")
//    @ResponseBody
//    public  List<UserNPRDTOForVUEList> getUserList(@PathVariable("shifruni") int shifruni,@PathVariable("shifrfac") int shifrfac,@PathVariable("shifrkaf") int shifrkaf) {
//        List<UserNPRDTOForVUEList> uList;
//        logger.debug("Try find users for shifruni = "+shifruni+" shifrfac="+shifrfac+" shifrkaf="+shifrkaf);
//
//        uList=userService.getAllDTOForUniFacKaf(shifruni,shifrfac,shifrkaf);
//        return uList;
//    }
    @RequestMapping(value="/users/{shifrpre}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<UserNPRDTOForVUEList> getUserList(@PathVariable("shifrpre") int shifrpre) {
        List<UserNPRDTOForVUEList> uList;
        logger.debug("Try find users for shifrpre = "+shifrpre);

        UserInfo userInfo=new UserInfo();

//        uList=userService.getPageDTOForPodr(shifrPre,pageNo,pageSize);
        uList=userService.getAllDTOForPodr(shifrpre,0);
        for (UserNPRDTOForVUEList it:uList) {
            if (userInfo.isAdmin())
                it.setCanbedeleted("true");
            else
                it.setCanbedeleted("false");
        }

        return uList;
    }

    @RequestMapping(value="/apusers/{shifrpre}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  String getAmntOfPageUserList(@PathVariable("shifrpre") int shifrpre) {
        List<UserNPRDTOForVUEList> uList;
        logger.debug("Get amnt of user pages for shifrpre = "+shifrpre);

        Integer amntOfPages=userService.getCountUserForPodr(shifrpre);

        return amntOfPages.toString();
    }



    @RequestMapping(value="/users/{shifrpre}/{pageno}/{pagesize}/{order}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<UserNPRDTOForVUEList> getPageUserList(@PathVariable("shifrpre") int shifrPre,
                                                       @PathVariable("pageno")   int pageNo,
                                                       @PathVariable("pagesize") int pageSize,
                                                       @PathVariable("order") int order
    ) {
        List<UserNPRDTOForVUEList> uList;
        logger.debug("Try find page "+pageNo+" users for shifrpre = "+shifrPre);
        UserInfo userInfo=new UserInfo();
        logger.debug("step2 in usser2 list");

        uList=userService.getPageDTOForPodr(shifrPre,pageNo,pageSize,order);
        logger.debug("amnt of rec in userlist "+uList.size());
        for (UserNPRDTOForVUEList it:uList) {
            if (userInfo.isAdmin())
                it.setCanbedeleted("true");
            else
                it.setCanbedeleted("false");
        }
        return uList;
    }


    @RequestMapping(value="/usteps",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> getUStepsList() {
        List<ItemErrorDTO> ustepslist;

        ustepslist=uStepService.getAllForDTO();
        logger.debug("Try load ustepslist. Amnt of recs= "+ustepslist.size());
        return ustepslist;
    }

    @RequestMapping(value="/user/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public UserEntity getUserById(@PathVariable("id") int id) {
        UserEntity userEntity;
        if (id>0) {
            userEntity=userService.getById(id);
            if ((userEntity.getShifrPodr()>0)
                    && (userEntity.getShifrUni()<1)
                    && (userEntity.getShifrFac()<1)
                    && (userEntity.getShifrKaf()<1)) {
                List<Integer> ufk = podrService.getUniFacKaf(userEntity.getShifrPodr());
                userEntity.setShifrUni(ufk.get(0));
                userEntity.setShifrFac(ufk.get(1));
                userEntity.setShifrKaf(ufk.get(2));
            } else 
            if ((userEntity.getShifrPodr()<1)
                 && (userEntity.getShifrUni()>0)) {
                 if  (userEntity.getShifrKaf()>0) userEntity.setShifrPodr(userEntity.getShifrKaf());
                 else
                 if  (userEntity.getShifrFac()>0) userEntity.setShifrPodr(userEntity.getShifrFac());
                 else userEntity.setShifrPodr(userEntity.getShifrUni());
            }
        } else {
            userEntity=new UserEntity();
        }
        return userEntity;
    }
    @RequestMapping(value="/user/setverified/{id}/{needEMail}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> setUserVerified(@PathVariable("id") int id,@PathVariable("needEMail") int needEMail) {
        List<ItemErrorDTO> errors=new ArrayList<ItemErrorDTO>();
        UserInfo userInfo=new UserInfo();
        if (!userInfo.isAdmin()) {
            errors.add(new ItemErrorDTO(3,"Wrong right ","Unauthorised:"));
            return errors;
        }
        logger.debug("*** setUserVerified. ***");
        String reqURL=httpServletRequest.getRequestURL().toString();
        String address;
        URL myURL;
        StringBuilder sb=new StringBuilder();
        try {
           myURL = new URL(reqURL);
           sb.append(myURL.getProtocol());
           sb.append("://"); 
           sb.append(myURL.getHost());
           sb.append(":");
           sb.append(myURL.getPort());
           sb.append(servletContextPath);
           address=sb.toString();
        } catch (MalformedURLException e) {
          myURL=null;
          address="Ошибка";
          logger.debug("malformed url exception.");
        }
//        logger.debug("*** "+address);
//        logger.debug("*** "+myURL);
        URL url;
//        try {
//            url=session.getServletContext().getResource(servletContextPath);
//        } catch (MalformedURLException e) {
//          url=null;
//          logger.debug("malformed url exception.");
//        }
//        logger.debug("URL="+url.toString());
//        id=0;
        if (id>0) {
            UserEntity userEntity;
            userEntity=userService.getById(id);
            int shifrIdUser;
            shifrIdUser=(int)userInfo.getUserDTO().getId();
            userEntity.setShifrIdSup(shifrIdUser);
            String fio=(String)userInfo.getUserDTO().getFIO();
            userEntity.setVerifiedSupFIO(fio);
            userEntity.setStatusCode(1);
            userService.setVerified(userEntity);
            String newPwd;
            newPwd="";
            if (needEMail==1) {
                newPwd=userService.sendPasswordForUser(userEntity,address);
            }
            if (newPwd.length()>2) {
                errors.add(new ItemErrorDTO(1,"Ok",newPwd));
            } else {
                errors.add(new ItemErrorDTO(1,"Ok","Ok"));
            }
        } else {
            errors.add(new ItemErrorDTO(2,"User missing in request ","Missing Id"));
        }
        return errors;
    }
    @RequestMapping(value="/user/del/{id}",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> deleteUserRec(@PathVariable("id") int id) {
        logger.debug("delete user id= " + id);
        UserInfo userInfo=new UserInfo();
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        if (userInfo.isAdmin()) {
           userService.deleteRecord(id);
           okList.add(new ItemErrorDTO(1, "Ok", "Ok"));
        }
        else {
            okList.add(new ItemErrorDTO(2, "Have not right", "Err"));
        }
        return okList;
    }


    @RequestMapping(value="/roles",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<RoleEntity> getRolesList() {
        List<RoleEntity> roleList;

        roleList=roleService.getAll();
        logger.debug("Try load rolesList. Amnt of recs= "+roleList.size());
        return roleList;
    }
    @RequestMapping(value="/user/roles/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> getUserRolesList(@PathVariable("id") int id) {
        List<UsersRolesEntity> urList;

        urList=urService.getAllForUser(id);
        logger.debug("Try load rolesList for user id="+id+". Amnt of recs= "+urList.size());
        List<ItemErrorDTO> ansList=new ArrayList<ItemErrorDTO>();
        if (urList.size()>0) {
            for (UsersRolesEntity urEntity:urList) {
                ansList.add(new ItemErrorDTO(urEntity.getShifrIdRole(),"",""));
            }
        }
        if (ansList.size()<1) {
            ansList.add(new ItemErrorDTO(0,"Not","Not"));
        }
//        logger.debug("Try transfer length="+ansList.size());
        return ansList;
    }
    @RequestMapping(value="/user/roles",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> saveUserRolesList(@RequestParam("id") int id,@RequestParam("roles") String roles) {
        urService.deleteRecordsForUser(id);
        if (roles.trim().length()>0) {
            String[] s=roles.trim().split(",");
            for (String c:s){
                int val=Integer.parseInt(c);
                if (val>0) {
                    UsersRolesEntity urEntity=new UsersRolesEntity();
                    urEntity.setShifrIdRole(val);
                    urEntity.setShifrIdUser(id);
                    urService.insertRecord(urEntity);
                }
            }
        }
        logger.debug("Try update user roles for user id="+id+" roles="+roles);
        List<ItemErrorDTO> ansList=new ArrayList<ItemErrorDTO>();
        ansList.add(new ItemErrorDTO(1,"Ok","Ok"));
        return ansList;
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST, produces = "application/json",consumes ="application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveUser(@RequestBody UserEntity uEntity, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        
        if (uEntity==null) {
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(1, "Empty User", "Empty User"));
            return errList;
        }
        
//        if (uEntity.getId()<1) {
//            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
//            errList.add(new ItemErrorDTO(2, "Wrong Empty User ID", "Wrong Empty User ID"));
//            return errList;
//        }
        int action=2;
        if (uEntity.getId()<1)
            action=1;
        UserEntity secondEntity;
        if (uEntity.getId()>0)
           secondEntity=userService.getById((int)uEntity.getId());
        else
           secondEntity=null;
        logger.debug("enter in save userEntity for " + uEntity.toString());
        userValidator.validate(uEntity, result);
        logger.debug("after validate ");
        if (result.hasErrors()) {
            logger.debug("save userEntity result has errors "+result.getAllErrors().size());
            return getErrList(result);
        }
        logger.debug("save user validator passed");
        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attempting to bind disallowed fields: " +
                    StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        logger.debug("save userEntity row for " + uEntity.toString());
        if (secondEntity!=null) {
           uEntity.setDataCreate(secondEntity.getDataCreate());
           uEntity.setDataDelete(secondEntity.getDataDelete());
           uEntity.setStatusCode(secondEntity.getStatusCode());
           uEntity.setVerifiedSupFIO(secondEntity.getVerifiedSupFIO());
           uEntity.setShifrIdSup(secondEntity.getShifrIdSup());
        } else {
           java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
           uEntity.setDataCreate(sqlDate);
        }
        if (uEntity.getShifrKaf()>0) {
            uEntity.setShifrPodr(uEntity.getShifrKaf());
        }
        else
        if (uEntity.getShifrFac()>0) {
            uEntity.setShifrPodr(uEntity.getShifrFac());
        }
        else
        if (uEntity.getShifrUni()>0) {
            uEntity.setShifrPodr(uEntity.getShifrUni());
        }
        userService.saveRecord(uEntity);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        int id=0;
        if (action==1) id=(int)uEntity.getId();
        okList.add(new ItemErrorDTO(id, "Ok", "Ok"));
        return okList;
    }
    @RequestMapping(value="/pwd/save",method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> setUserPassword(@RequestBody ChgPwdDTO up, BindingResult result, HttpSession session, Model model) {
        List<ItemErrorDTO> errors=new ArrayList<ItemErrorDTO>();
        if (up==null) {
            errors.add(new ItemErrorDTO(1, "Empty Record for Password Change", "Empty Record"));
            return errors;
        }
        chgPwdValidator.validate(up, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        int shifrIdUser;
        UserInfo userInfo=new UserInfo();
        shifrIdUser=(int)userInfo.getUserDTO().getId();
        if (shifrIdUser<1) {
            errors.add(new ItemErrorDTO(2,"User missing in request ","Missing Id"));
            return errors;
        }
        if (shifrIdUser!=up.getIduser()) {
            errors.add(new ItemErrorDTO(3, "Wrong userCode", "Wrong userCode"));
            return errors;
        }
        up.setPwd(up.getPwd().trim());

        userService.setPassword(up);
        errors.add(new ItemErrorDTO(1,"Ok","Ok"));
        logger.debug("save paswsword " + up.getPwd() +" for userid="+up.getIduser());

        return errors;
    }



    @RequestMapping(value="/user/name/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> getUserNameById(@PathVariable("id") int id) {
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        logger.debug("get user name for id="+id);
        String name=userService.getNameById(id);
//        logger.debug("Try transfer length="+ansList.size());
        okList.add(new ItemErrorDTO(0,name.trim(),"Ok"));
        return okList;
    }
    @RequestMapping(value="/chklogin/{login}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> checkNewLogin(@PathVariable("login") String login) {
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        if (login==null) {
            logger.debug("perform check login = Empty login");
            okList.add(new ItemErrorDTO(3,"Error: empty string","Err"));
            return okList;
        }
        logger.debug("perform check login = "+login);
        if (login.length()<1) {
            okList.add(new ItemErrorDTO(4,"Error: short string","Err"));
            return okList;
        }
        boolean retVal=userService.checkLogin(login);
//        logger.debug("Try transfer length="+ansList.size());
        if (retVal) {
            okList.add(new ItemErrorDTO(1,"Ok","Ok"));
        } else {
            okList.add(new ItemErrorDTO(2,"Exists","Exist"));
        }
        return okList;
    }


    @RequestMapping(value="/preunifackaf/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> getUniFacKafByShifrPre(@PathVariable("id") int id) {
        List<ItemErrorDTO> ansList=new ArrayList<ItemErrorDTO>();
        List<Integer> ufk = podrService.getUniFacKaf(id);
        logger.debug("getting ufk for shifrpre ="+id);
        ansList.add(new ItemErrorDTO(ufk.get(0),"uni","uni"));
        ansList.add(new ItemErrorDTO(ufk.get(1),"fac","fac"));
        ansList.add(new ItemErrorDTO(ufk.get(2),"kaf","kaf"));
        return ansList;
    }

    @RequestMapping(value="/prcname/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> getPredpCompoundName(@PathVariable("id") int id) {
        List<ItemErrorDTO> ansList=new ArrayList<ItemErrorDTO>();
        String name = podrService.buildCompoundName(id);
//        logger.debug("getting compoundname for shifrpre ="+id+" name="+name);
        ansList.add(new ItemErrorDTO(0,name,"Ok"));
        return ansList;
    }
// Показатели

    @RequestMapping(value="/pokazs",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<PokazEntity> getPokazList() {
        List<PokazEntity> pokazList;

        pokazList=pokazService.getAll();
        logger.debug("Try load pokazList. Amnt of recs= "+pokazList.size());
        return pokazList;
    }

    @RequestMapping(value = "/pokaz/save", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> savePokazEntity(@RequestBody PokazEntity pEntity, BindingResult result, HttpSession session, Model model) {
        int action=2;
        if (pEntity==null) {
            logger.debug("Empty PokazRec row for saving");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(2, "Err", "Пустая запись"));
            return errList;
        }
        if (pEntity.getId()==0) {
            action=1;
        }
        logger.debug("save PokazRec row for " + pEntity.toString());
//        System.out.println("save UnivRec row for " + pEntity.toString());
        pokazValidator.validate(pEntity, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
//        logger.debug("save UnivRec row for " + pEntity.toString());
        pokazService.saveRecord(pEntity);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        long v=0;
        if (action==1) v=pEntity.getId();
        okList.add(new ItemErrorDTO((int)v, "Ok", "Ok"));
        return okList;
    }

// Должности

    @RequestMapping(value="/dolgs",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<DolgEntity> getDolgList() {
        List<DolgEntity> dolgList;

        dolgList=dolgService.getAll();
        logger.debug("Try load dolgList. Amnt of recs= "+dolgList.size());
        return dolgList;
    }

    @RequestMapping(value = "/dolg/save", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveDolgEntity(@RequestBody DolgEntity dEntity, BindingResult result, HttpSession session, Model model) {
        int action=2;
        if (dEntity==null) {
            logger.debug("Empty DolgRec row for saving");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(2, "Err", "Пустая запись"));
            return errList;
        }
        if (dEntity.getId()==0) {
            action=1;
        }
        logger.debug("save DolgRec row for " + dEntity.toString());
//        System.out.println("save UnivRec row for " + pEntity.toString());
        dolgValidator.validate(dEntity, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
//        logger.debug("save UnivRec row for " + pEntity.toString());
        dolgService.saveRecord(dEntity);
        logger.debug("dolgServic.saveRecord executed");
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        long v=0;
        if (action==1) v=dEntity.getId();
        okList.add(new ItemErrorDTO((int)v, "Ok", "Ok"));
        return okList;
    }

    @RequestMapping(value = "/dolg/delete/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> deleteDolgEntity(@PathVariable("id") int id) {
        UserInfo userInfo=new UserInfo();
        if (!userInfo.isAdmin()) {
            logger.debug("Not enought rights for this operation");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(3, "Err", "Недостаточно прав"));
            return errList;

        }

        if (id<=0) {
            logger.debug("Empty dolgRec for deleting");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(2, "Err", "Пустая запись"));
            return errList;

        }
        logger.debug("del DolgRec row id= " + id);
//        logger.debug("save UnivRec row for " + pEntity.toString());
        dolgService.deleteRecord(id);
        logger.debug("dolgServic.deleteRecord executed");
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        long v=0;
        okList.add(new ItemErrorDTO((int)v, "Ok", "Ok"));
        return okList;
    }


    @RequestMapping(value="/ntrs/{shifrpre}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<NtrRecDTO> getNtrList(@PathVariable("shifrpre") int shifrpre) {
        List<NtrRecDTO> ntrList;
        logger.debug("Try find ntrList for shifrpre = "+shifrpre);
        ntrList=ntrService.getNtrListForPre(shifrpre);
        return ntrList;
    }
    @RequestMapping(value="/ntrsnpr/{shifrnpr}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<NtrRecDTO> getNtrListForUser(@PathVariable("shifrnpr") int shifrnpr) {
        List<NtrRecDTO> ntrList;
        logger.debug("Try find ntrList for shifrusr = "+shifrnpr);
        ntrList=ntrService.getNtrListForNPR(shifrnpr);
        for (NtrRecDTO n:ntrList) {
            logger.debug(n.getId()+" "+n.getAuthors());
        }
        return ntrList;
    }
//---------------
    @RequestMapping(value="/ntrs/{kind}/{shifrpre}/{yfr}/{yto}/{pageno}/{pagesize}/{order}/{shifridnprfilter}/{shifriddetfilter}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<NtrRecDTO> getPageNtrList(@PathVariable("kind") int kind,
                                           @PathVariable("shifrpre") int shifrpre,
                                           @PathVariable("yfr") int yfr,
                                           @PathVariable("yto") int yto,
                                           @PathVariable("pageno")   int pageNo,
                                           @PathVariable("pagesize") int pageSize,
                                           @PathVariable("order") int order,
                                           @PathVariable("shifridnprfilter") int shifridnprfilter,
                                           @PathVariable("shifriddetfilter") int shifriddetfilter
        ) {
        int ytoself,yfromself,shifridnprfilterself,shifriddetfilterself;
        yfromself = 1960;
        if (yfr>0)
            yfromself=yfr;
        ytoself   = GregorianCalendar.getInstance().get(Calendar.YEAR);
        if (yto>0)
            ytoself=yto;
        shifridnprfilterself=0;
        if (shifridnprfilter>0)
            shifridnprfilterself=shifridnprfilter;
        shifriddetfilterself=0;
        if (shifriddetfilter>0)
            shifriddetfilterself=shifriddetfilter;
        List<NtrRecDTO> ntrList;
        logger.debug("Try find ntrList page "+pageNo+" ntrs for shifrpre = "+shifrpre);
        ntrList=ntrService.getPageNtrListForPre(kind,shifrpre,yfromself,ytoself,pageNo,pageSize,order,shifridnprfilterself,shifriddetfilterself);
        return ntrList;
    }
    @RequestMapping(value="/apntrs/{shifrpre}/{mode}/{yfr}/{yto}/{shifridnprforfilter}/{shifriddetforfilter}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  String getAmntOfPageNtrList(@PathVariable("shifrpre") int shifrpre,
                                        @PathVariable("mode") int mode,
                                        @PathVariable("yfr") int yfr,
                                        @PathVariable("yto") int yto,
                                        @PathVariable("shifridnprforfilter") int shifridnprforfilter,
                                        @PathVariable("shifriddetforfilter") int shifriddetforfilter) {
        logger.debug("Get amnt of ntr pages for shifrpre = "+shifrpre+" mode="+mode);

        Integer amntOfPages=ntrService.getCountNtr(mode,shifrpre,yfr,yto,shifridnprforfilter,shifriddetforfilter);

        return amntOfPages.toString();
    }


    @RequestMapping(value="/ntr/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public NtrRecFromJSONDTO getNtrById(@PathVariable("id") int id) {
        NtrEntity ntrEntity;
        NtrRecFromJSONDTO ntrRecFromJSONDTO=new NtrRecFromJSONDTO();
        if (id>0) {
            ntrEntity=ntrService.getById(id);
            ntrRecFromJSONDTO.setId(ntrEntity.getId());
            ntrRecFromJSONDTO.setName(ntrEntity.getName());
            ntrRecFromJSONDTO.setParametry(ntrEntity.getParametry());
            ntrRecFromJSONDTO.setDatePublJava((GregorianCalendar) ntrEntity.getDatepubl());
            ntrRecFromJSONDTO.setAmntOfImages(ntrService.getAmntOfImagesForNtr(ntrEntity.getId()));
            ntrRecFromJSONDTO.setAmntOfDocs(ntrService.getAmntOfDocsForNtr(ntrEntity.getId()));
            ntrRecFromJSONDTO.setShifrPre(ntrEntity.getShifrpre());
            String d;
            if (ntrRecFromJSONDTO.getDatePublJava()!=null) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                d=sdf.format(ntrEntity.getDatepubl().getTime());
            } else {
                d="";
            }
            ntrRecFromJSONDTO.setDatepubl(d);
            ntrRecFromJSONDTO.setFioapproved(ntrEntity.getFioapprove());
            ntrRecFromJSONDTO.setApproved("");
            String fioApproved;
            fioApproved="";
            if (ntrEntity.getFioapprove()!=null)
                if (ntrEntity.getFioapprove().trim().length()>0)
                    fioApproved=ntrEntity.getFioapprove().trim();
            ntrRecFromJSONDTO.setApproved(fioApproved);
            if (fioApproved.trim().length()>0) {
                ntrRecFromJSONDTO.setApproved("Подтверждено");
            } else {
                ntrRecFromJSONDTO.setApproved("");
            }
            String da;
            if (ntrRecFromJSONDTO.getDataapproved()!=null) {
                SimpleDateFormat sdfa=new SimpleDateFormat("yyyy-MM-dd");
                da=sdfa.format(ntrEntity.getDateapprove().getTime());
            } else {
                da="";
            }
            ntrRecFromJSONDTO.setDataapproved(da);
            ntrRecFromJSONDTO.setIdRinc(ntrEntity.getIdRinc());
            ntrRecFromJSONDTO.setHrefRinc(ntrEntity.getHrefRinc());
        }
        return ntrRecFromJSONDTO;
    }
    
    
    @RequestMapping(value = "/ntr/save", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveNtrRec(@RequestBody NtrRecFromJSONDTO ntrRecFromJSONDTO, BindingResult result, HttpSession session, Model model) {
        int action=1;
        logger.debug("saving ntr datepubl ="+ntrRecFromJSONDTO.getDatepubl());
        GregorianCalendar d;
        if (ntrRecFromJSONDTO==null) {
            logger.debug("Empty NtrRec for saving");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(1, "Err", "Пустая запись"));
            return errList;
        }
        if (ntrRecFromJSONDTO.getDatepubl()!=null)
            if (ntrRecFromJSONDTO.getDatepubl().trim().length()==10) 
                if (ntrRecFromJSONDTO.getDatepubl().trim().contains("-")) {
                   String[] sdt;
                   sdt=ntrRecFromJSONDTO.getDatepubl().split("-");
                   if (sdt.length==3) {
                      int y,m,dd;
                      y=Integer.parseInt(sdt[0]);
                      m=Integer.parseInt(sdt[1]);
                      dd=Integer.parseInt(sdt[2]);
                      d=new GregorianCalendar(y,m-1,dd);
                      ntrRecFromJSONDTO.setDatePublJava(d);
                   }
                }
        if (ntrRecFromJSONDTO.getId()<1)
            action=1;
        logger.debug("save NtrRec for ntr id= " + ntrRecFromJSONDTO.getId());
//        System.out.println("save UnivRec row for " + pEntity.toString());
        ntrRecFromJSONDTOValidator.validate(ntrRecFromJSONDTO, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        int id=ntrService.saveNtrRecFromJSONDTO(ntrRecFromJSONDTO);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(id, "Ok", "Ok"));
        return okList;
    }

    @RequestMapping(value = "/ntr/del/{id}", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> delNtrRec(@PathVariable("id") int id) {
        logger.debug("deleting ntr for id = "+id);
        if (id<=0) {
            logger.debug("Wrong id for ntr deleting");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(1, "Err", "Wrong id "+id));
            return errList;
        }
        boolean hasRights=false;
        UserInfo userInfo=new UserInfo();
        boolean isAdmin=userInfo.isAdmin() || userInfo.isDataAdmin();
        if (!isAdmin) {
            NtrEntity ne=ntrService.getById(id);
            for (NtrAuthEntity nae : ntrAuthService.getAllForNtr(id)) {
                if (nae.getIdauth()==userInfo.getShifrWrk()) {
                    hasRights=true;
                    break;
                }
            }
        } else {
            hasRights = true;
        }
        if (!hasRights) {
            logger.debug("Wrong right for ntr deleting.");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(1, "Err", "Wrong right"));
            return errList;
        }
        ntrService.deleteRecord(id);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(1, "Ok", "Ok"));
        return okList;
    }

    @RequestMapping(value = "/ntr/chk", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveNtrRec(@RequestBody NtrNameJSONDTO ntrNameJSONDTO, BindingResult result, HttpSession session, Model model) {

    try {
        String nn=ntrNameJSONDTO.getName()+"\n";
        logger.debug("checking ntr ="+ntrNameJSONDTO.getName());
    } catch (Exception e) {}
        if (ntrNameJSONDTO==null || ntrNameJSONDTO.getName()==null || ntrNameJSONDTO.getName().length()<1) {
            logger.debug("Empty Name for checking");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(-1, "Err", "Пустая запись"));
            return errList;
        }
        String name = NormalizeString.executeNormalizeString(ntrNameJSONDTO.getName());
        int id=ntrService.chkExistsSuchNTR(name);
        if (id<1) {
            List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
            okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
            return okList;
        }
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        NtrEntity ne=ntrService.getById(id);
        String au=ntrService.getAuthors(id,0);
        okList.add(new ItemErrorDTO(id, ne.getName(), au));
        return okList;
    }
    
    
    @RequestMapping(value="/semanticui/dropdown/tags/{query}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public SemanticUIDropDownDTO getSemanticUIDropDownList(@PathVariable("query") String query) {
//        System.setProperty("console.encoding","Cp866");
        String fio;
        fio=query;
        SemanticUIDropDownDTO suddd;
//        System.out.println("query="+query);
        if (query == null || query.trim().isEmpty()) {
            suddd=new SemanticUIDropDownDTO(false);
            logger.debug("Wrong empty query for author dropdown="+query);
            return suddd;
        }
        Pattern p = Pattern.compile("[^а-я ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(query);
        boolean b = m.find();
        if (!b) {
            suddd=new SemanticUIDropDownDTO(false);
            logger.debug("Wrong query for author dropdown="+query);
        } else {
            fio=query.trim().toUpperCase();
//            System.out.println("uppercase query="+fio);
//            query=query.trim().toUpperCase();
            logger.debug("before get matching records for "+fio);
            suddd=userService.getSourceForSemanticUIDropDown(fio.trim().toUpperCase());
        }
        logger.debug("Try find FIO for query = "+fio);
        if (suddd.isSuccess())
            logger.debug("finded "+suddd.getResults().size()+" records.");
        else
            logger.debug("cannot find any matchig records for "+fio);

        return suddd;
    }

    @RequestMapping(value="/semanticui/dropdown/tags",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public SemanticUIDropDownDTO getSemanticUIDropDownListEmpty() {
//        System.setProperty("console.encoding","Cp866");
        SemanticUIDropDownDTO suddd;
//        System.out.println("query="+query);
        suddd=new SemanticUIDropDownDTO(false);
        logger.debug("Empty query for DDB");
        return suddd;
    }

    @RequestMapping(value="/semanticui/search",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public SemanticUISearchDTO getSemanticUINtrSearchList(@RequestParam(value = "q",required = true) String query) {
//        System.setProperty("console.encoding","Cp866");
        String name;
        name=query;
        SemanticUISearchDTO susd;
//        System.out.println("query="+query);
        if (query == null || query.trim().isEmpty()) {
            susd=new SemanticUISearchDTO(false);
            logger.debug("Wrong empty query for search ntr name ="+query);
            return susd;
        }
        Pattern p = Pattern.compile("[^а-я ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(query);
        boolean b = m.find();
//        logger.debug("b="+b);
        b=true;
        if (!b) {
            susd=new SemanticUISearchDTO(false);
//            logger.debug("Wrong query for ntr name search="+query);
        } else {
            name=query.trim().toUpperCase();
//            System.out.println("uppercase query="+fio);
//            query=query.trim().toUpperCase();
//            logger.debug("before get matching records for "+name);
            susd=ntrService.getSourceForSemanticUISearch(name);
//            logger.debug("amnt of finded "+susd.getItems().size());
        }
//        logger.debug("Try find Name for query = "+name);
//        if (susd.getTotalCount()>0)
//            logger.debug("finded "+susd.getItems().size()+" records.");
//        else
//            logger.debug("cannot find any matchig records for "+name);

        return susd;
    }


    @RequestMapping(value = "/ntr/approve/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> approveNtrRec(@PathVariable("id") int id) {
        logger.debug("start approve ntr id="+id);
        GregorianCalendar d;
        if (id<=0) {
            logger.debug("Wrong record for approving ");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(1, "Err", "Пустая запись"));
            return errList;
        }
        UserInfo userInfo=new UserInfo();
        int shifrIdUser;
        shifrIdUser=(int)userInfo.getUserDTO().getId();
        String fio= userInfo.getUserDTO().getFIO();
        UserEntity userEntity = userService.getById(shifrIdUser);
        try {
            fio = userEntity.getFam().trim()+" "+userEntity.getNam().trim()+" "+userEntity.getOtc().trim();
        } catch (NullPointerException npe) {
            fio = userEntity.getName();
        }
        d = new GregorianCalendar();
        SimpleDateFormat sdf=new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String df=sdf.format(d.getTime());
//        logger.debug("aproved NtrRec for ntr id= " + id+ " date="+df);
        ntrService.approveNtrRec(id,shifrIdUser);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(id, fio, df));
        return okList;
    }
    @RequestMapping(value = "/ntr/dismissapprove/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> dismissApproveNtrRec(@PathVariable("id") int id) {
        logger.debug("start dismissapprove ntr id="+id);
        if (id<=0) {
            logger.debug("Wrong record for approving ");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(1, "Err", "Пустая запись"));
            return errList;
        }
        logger.debug("disaproved NtrRec for ntr id= " + id);
        ntrService.dismissApproveNtrRec(id);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(id, "Ok", "Ok"));
        return okList;
    }


    @RequestMapping(value="/author/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> getNtrAuthorsList(@PathVariable("id") int id) {
        List<ItemErrorDTO> okList=new ArrayList<ItemErrorDTO>();
        if (id<1) {
            okList.add(new ItemErrorDTO(1,"Error","Err")); 
            return okList;
        } 
        List<NtrAuthEntity> authors=ntrAuthService.getAllForNtr(id);
        if (authors==null) {
            okList.add(new ItemErrorDTO(2,"Empty","Err"));
            return okList;
        }
        if (authors.size()<1) {
            okList.add(new ItemErrorDTO(2,"Empty","Empty"));
            return okList;
        }
        for (NtrAuthEntity naEntity:authors) {
            String s = naEntity.getName();
            String procent=""+naEntity.getProcent();
            
            if (
                 (naEntity.getIdauth()>0) &&
                 (
                   (naEntity.getName()==null) ||
                   (naEntity.getName().trim().length()<1)
                 )
               ) {
                  UserEntity userEntity = userService.getById(naEntity.getIdauth());
                  s = userEntity.getFam().trim()+" "+userEntity.getNam().trim()+" "+userEntity.getOtc().trim();
            }
                
                          
                        

            okList.add(new ItemErrorDTO(naEntity.getIdauth(),s,procent));
        }

        return okList;
    }

    @RequestMapping(value="/authorntr/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> getNtrAuthorsListAsString(@PathVariable("id") int idntr) {
        List<ItemErrorDTO> okList=new ArrayList<ItemErrorDTO>();
        if (idntr<1) {
            okList.add(new ItemErrorDTO(1,"Error","Err"));
            return okList;
        }
        String authString=ntrAuthService.getAuthorsForNtr(idntr,0);
        if (authString==null) {
            okList.add(new ItemErrorDTO(2,"Empty","Err"));
            return okList;
        }
        if (authString.trim().length()==0) {
            okList.add(new ItemErrorDTO(2,"Empty","Err"));
            return okList;
        }
        okList.add(new ItemErrorDTO(0,authString,"Ok"));

        return okList;
    }
    
    @RequestMapping(value = "/authors/save", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveNtrAuthorsList(@RequestBody NtrAuthorsDTO ntrAuthorsDTO, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        if (ntrAuthorsDTO==null) {
            logger.debug("Empty NtrAuthorList for saving");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Пустая запись"));
            return errList;
        }
        if (ntrAuthorsDTO.getId()<1) {
            logger.debug("Ntr id was  not found in JSON REC in saving ntrauthorslist");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Пустая запись"));
            return errList;
        }
        logger.debug("save NtrAuthors for ntr id= " + ntrAuthorsDTO.getId());
//        System.out.println("save UnivRec row for " + pEntity.toString());
        ntrAuthorsDTOValidator.validate(ntrAuthorsDTO, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        ntrService.saveNtrAuthorsList(ntrAuthorsDTO);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        return okList;
    }


    
    @RequestMapping(value="/ntrpokazlist/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> getNtrPokazList(@PathVariable("id") int id) {
        List<ItemErrorDTO> okList=new ArrayList<ItemErrorDTO>();
        if (id<1) {
            okList.add(new ItemErrorDTO(1,"Error","Err"));
            return okList;
        }
        List<NtrPokazEntity> ntrPokazList=ntrPokazService.getAllForNtr(id);
        if (ntrPokazList==null) {
            okList.add(new ItemErrorDTO(2,"Empty","Err"));
            return okList;
        }
        if (ntrPokazList.size()<1) {
            okList.add(new ItemErrorDTO(2,"Empty","Empty"));
            return okList;
        }
        for (NtrPokazEntity ntrPokazEntity:ntrPokazList) {
            int n = ntrPokazEntity.getIdpokaz();
            okList.add(new ItemErrorDTO(n,"Ok","Ok"));
        }

        return okList;
    }
    @RequestMapping(value="/pokazsntr/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> getNtrPokazString(@PathVariable("id") int id) {
//--
        List<ItemErrorDTO> okList=new ArrayList<ItemErrorDTO>();
        if (id<1) {
            okList.add(new ItemErrorDTO(1,"Error","Err"));
            return okList;
        }
        String pokazsString=ntrPokazService.getPokazForNtr(id);
        if (pokazsString==null) {
            okList.add(new ItemErrorDTO(2,"Empty","Err"));
            return okList;
        }
        if (pokazsString.trim().length()==0) {
            okList.add(new ItemErrorDTO(2,"Empty","Err"));
            return okList;
        }
        okList.add(new ItemErrorDTO(0,pokazsString,"Ok"));

        return okList;
    }

    @RequestMapping(value = "/ntrpokaz/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> saveNtrPokazList(@RequestParam("id") int id,@RequestParam("pokaz") String pokazs) {
        List<ItemErrorDTO> ansList;
        ansList=new ArrayList<ItemErrorDTO>();
        if (id>0) {
            ntrPokazService.deleteRecordsForNtr(id);
            if (pokazs.trim().length()>0) {
                String[] s=pokazs.trim().split(",");
                for (String c:s){
                    int val=Integer.parseInt(c);
                    if (val>0) {
                        NtrPokazEntity ntrPokazEntity=new NtrPokazEntity();
                        ntrPokazEntity.setIdpokaz(val);
                        ntrPokazEntity.setIdntr(id);
                        ntrPokazService.insertRecord(ntrPokazEntity);
                    }
                }
            }
            logger.debug("Try update ntr pokaz for user id="+id+" pokazlist="+pokazs);
            ansList.add(new ItemErrorDTO(1,"Ok","Ok"));
        }
        if (ansList.size()<1) {
            ansList.add(new ItemErrorDTO(9,"Err","Err"));
        }
        return ansList;
    }
    @RequestMapping(value="/ntrdoclist/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<NtrDocRecDTO> getNtrDocList(@PathVariable("id") int id) {
        List<NtrDocRecDTO> ntrDocList;
        if (id<1) {
            NtrDocRecDTO ntrDocRecDTO=new NtrDocRecDTO();
            ntrDocRecDTO.setId(0);
            ntrDocRecDTO.setComment("Error");
            ntrDocList=new ArrayList<NtrDocRecDTO>();
            ntrDocList.add(ntrDocRecDTO);
            return ntrDocList;
        }
        ntrDocList=ntrDocService.getListOfDTOForNtr(id);
        if (ntrDocList==null) {
            NtrDocRecDTO ntrDocRecDTO=new NtrDocRecDTO();
            ntrDocRecDTO.setId(0);
            ntrDocRecDTO.setComment("Empty");
            ntrDocList=new ArrayList<NtrDocRecDTO>();
            ntrDocList.add(ntrDocRecDTO);
            return ntrDocList;
        }
        if (ntrDocList.size()<1) {
            NtrDocRecDTO ntrDocRecDTO=new NtrDocRecDTO();
            ntrDocRecDTO.setId(0);
            ntrDocRecDTO.setComment("Empty");
            ntrDocList=new ArrayList<NtrDocRecDTO>();
            ntrDocList.add(ntrDocRecDTO);
            return ntrDocList;
        }

        return ntrDocList;
    }

    @RequestMapping(value = "/ntrdocfile/save/{id}",method= RequestMethod.POST,produces = "application/json",consumes = "multipart/form-data")
    @ResponseBody
    public List<ItemErrorDTO>  saveNtrDoc(HttpServletRequest servletRequest,
                               @PathVariable("id") int ntrId,
                               @RequestParam("file")	MultipartFile	uploadedNtrDoc) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(uploadedNtrDoc, "uploadedNtrDoc");
        uploadedNtrDocValidator.validate(uploadedNtrDoc,bindingResult);
        if (bindingResult.hasErrors()) {
            int i=0;
            for (org.springframework.validation.ObjectError err:bindingResult.getAllErrors()) {
                logger.debug(""+(++i)+" "+err.getDefaultMessage());
            }
            
        }
        logger.debug("saveNtrDoc for ntrid= "+ntrId+" file="+uploadedNtrDoc.getOriginalFilename()+" "+uploadedNtrDoc.getSize()+" bytes");
        List<ItemErrorDTO> ansList;
        ansList=new ArrayList<ItemErrorDTO>();
//        MultipartFile multipartFile	= uploadedNtrDoc;
//        String	fileName	=	multipartFile.getOriginalFilename();
        NtrDocEntity ntrDocEntity=new NtrDocEntity();
        if (ntrId>0) ntrDocEntity.setIdntr(ntrId);
        String errorMes=new String();
        try	{
            byte[] r= ntrDocService.getFileIntoBytes(uploadedNtrDoc,ntrDocEntity,errorMes);
            ntrDocService.saveRecord(ntrDocEntity);
            ntrDocService.updateDocument(r,(int) ntrDocEntity.getId());
            logger.debug("size of downloading file="+r.length);
            ansList.add(new ItemErrorDTO((int)ntrDocEntity.getId(),"Ok","Ok"));

        }	catch	(Exception e)	{
            logger.debug("Exception "+e.getMessage());
            ansList.add(new ItemErrorDTO(9,"Err","Err"));
        }
        return ansList;
    }

    @RequestMapping(value = "/ntrdoclist/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> saveNtrDocList(@RequestParam("id") int id,@RequestParam("docids") String docids) {
        List<ItemErrorDTO> ansList;
        ansList=new ArrayList<ItemErrorDTO>();
        if (id>0) {
//            ntrPokazService.deleteRecordsForNtr(id);
            if (docids.trim().length()>0) {
                String[] s=docids.trim().split(",");
                for (String c:s){
                    int val=Integer.parseInt(c);
                    if (val>0) {
                        ntrDocService.updateIdNtr(id,val);
                    }
                }
            }
            logger.debug("Try update ntr doc for ntr id="+id+" docids="+docids);
            ansList.add(new ItemErrorDTO(1,"Ok","Ok"));
        }
        if (ansList.size()<1) {
            ansList.add(new ItemErrorDTO(9,"Err","Err"));
        }
        return ansList;
    }

    @RequestMapping(value="/ntrdoc/del/{id}",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public  List<ItemErrorDTO> deleteNtrDoc(@PathVariable("id") int id) {
        logger.debug("delete NtrDoc id= " + id);
        ntrDocService.deleteRecord(id);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(1, "Ok", "Ok"));
        return okList;
    }

    @RequestMapping(value="/ntrdochrefs/{id}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<String> getNtrDocHrefList(@PathVariable("id") int id,HttpServletRequest req) {
        String appPath=req.getContextPath();
        List<String> ntrDocHrefList=new ArrayList<String>();
        if (id<1) {
            ntrDocHrefList.add("");
            return ntrDocHrefList;
        }
        List<Integer> idsList=ntrDocService.getIdListOfImagesForNtr(id);
        if (idsList==null) {
            ntrDocHrefList.add("");
            return ntrDocHrefList;
        }
        if (idsList.size()<1) {
        }
        for (Integer i:idsList) {
            String s;
            s=appPath+"/util/ntrdoc/image/"+i;
            ntrDocHrefList.add(s);
        }
        return ntrDocHrefList;
    }
    
    @RequestMapping(value = "/ntrdoc/image/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable long id) {
        HttpHeaders headers = new HttpHeaders();
        byte[] media;
        NtrDocEntity ntrDocEntity;
        WidBlobEntity widBlobEntity;
        if (id>0) {
            media=ntrDocService.getBlobAsBytes(id);
            ntrDocEntity=ntrDocService.getById((int)id);
            if (ntrDocEntity.getIdwidblob()>0) {
                widBlobEntity=widBlobService.getById((int) ntrDocEntity.getIdwidblob());
            } else {
                widBlobEntity=null;
            }
            //     logger.debug("bytearay length="+media.length);
        } else {
            media         = null ;
            ntrDocEntity  = null ;
            widBlobEntity = null ;
        }
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        try {
            headers.setContentLength(media.length);
        }
        catch (Exception e) {
            headers.setContentLength(0);
        }
        try {
            if (widBlobEntity!=null)
                headers.setContentType(MediaType.parseMediaType(widBlobEntity.getMime()));
            else
                headers.setContentType(MediaType.TEXT_HTML);
        }
        catch (Exception e) {
            headers.setContentType(MediaType.TEXT_HTML);
        }

        //    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
        //    return responseEntity;
        return new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
//        return ResponseEntity.ok()
//                .contentLength(media.length)
//                .contentType(MediaType.parseMediaType(widBlobEntity.getMime())
//                .body(media);

    }

    @RequestMapping(value = "/ntrdoc/doc/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDocumentAsResponseEntity(@PathVariable long id) {
        logger.debug("request download file id="+id);
        HttpHeaders headers = new HttpHeaders();
        byte[] media;
        NtrDocEntity ntrDocEntity;
        WidBlobEntity widBlobEntity;
        if (id>0) {
            media = ntrDocService.getBlobAsBytes(id)      ;
            ntrDocEntity = ntrDocService.getById((int)id) ;
            if (ntrDocEntity.getIdwidblob()>0) {
                widBlobEntity = widBlobService.getById((int) ntrDocEntity.getIdwidblob());
            } else {
                widBlobEntity = null ;
            }
            logger.debug("bytearay length="+media.length);
        } else {
            media         = null ;
            ntrDocEntity  = null ;
            widBlobEntity = null ;
        }
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
//        String reqURL=httpServletRequest.getRemoteHost().toString();
//
//        headers.setAccessControlAllowOrigin("null");
//        Access-Control-Allow-Origin: http://mozilla.org
        try {
            headers.setContentLength(media.length);
        }
        catch (Exception e) {
            headers.setContentLength(0);
        }
        try {
            if (widBlobEntity!=null)
                headers.setContentType(MediaType.parseMediaType(widBlobEntity.getMime()));
            else
                headers.setContentType(MediaType.TEXT_HTML);
        }
        catch (Exception e) {
            headers.setContentType(MediaType.TEXT_HTML);
        }

        //    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
        //    return responseEntity;
        return new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.POST, produces = "application/json",consumes = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> sendCodeByMail(@RequestBody RegisterMailCodeDTO registerCodeDTO, BindingResult result, HttpSession session, Model model) {
       logger.debug("Try to send email");
       if (registerCodeDTO==null) {
            logger.debug("Empty email for code sending");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Пустая запись"));
            return errList;
        }
        if (registerCodeDTO.getEmail()==null) {
            logger.debug("Empty email for code sending");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Пустая запись"));
            return errList;
        }
        if (registerCodeDTO.getEmail().length()<3) {
            logger.debug("Wrong email for code sending");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Неверный адрес почты"));
            return errList;
        }
        String email=registerCodeDTO.getEmail();
        if (!mailService.isValidEmailAddress(email)) {
            logger.debug("Wrong email for code sending");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Неверный адресс почты"));
            return errList;
        }
        logger.debug("before sending mail "+email);
        String r = mailService.sendCodeByMail(email);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", r));
        return okList;
    }

    @RequestMapping(value = "/mail/send", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> sendCodeByMailGet() {
        RegisterMailCodeDTO registerCodeDTO=new RegisterMailCodeDTO();
        registerCodeDTO.setEmail("santono@mail.ru");
        logger.debug("Try to send email");
        if (registerCodeDTO==null) {
            logger.debug("Empty email for code sending");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Пустая запись"));
            return errList;
        }
        if (registerCodeDTO.getEmail()==null) {
            logger.debug("Empty email for code sending");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Пустая запись"));
            return errList;
        }
        if (registerCodeDTO.getEmail().length()<3) {
            logger.debug("Wrong email for code sending");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Неверный адресс почты"));
            return errList;
        }
        String email=registerCodeDTO.getEmail();
        if (!mailService.isValidEmailAddress(email)) {
            logger.debug("Wrong email for code sending");
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            errList.add(new ItemErrorDTO(0, "Err", "Неверный адресс почты"));
            return errList;
        }
        logger.debug("before sending mail "+email);
        String r = mailService.sendCodeByMail(email);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", r));
        return okList;
    }

    private List<ItemErrorDTO> getErrList(BindingResult result) {
        if (result.hasErrors()) {
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            int errCnt = result.getErrorCount();
            if (errCnt > 0) {
                int i;
                i = 0;
                for (ObjectError oError : result.getAllErrors()) {
                    ItemErrorDTO errDTO = new ItemErrorDTO(i++, oError.getDefaultMessage(), oError.getCode());
                    errList.add(errDTO);
                }
            }
            return errList;
        } else
            return null;

    }

    @RequestMapping(value = "/reportPokaz/{shifrPre}",method = RequestMethod.GET)
    public ModelAndView reportRoadList(@PathVariable int shifrPre,ModelAndView modelAndView, ModelMap model,HttpSession session) {
        logger.debug("Received request to download multi report shifrpre=" + shifrPre);

        String viewName=new String();
        JRDataSource datasource  = ntrService.getPokazDataSource(shifrPre);

        model.addAttribute("datasource", datasource);
        model.addAttribute("format", "pdf");
        String s;
        s=podrService.buildCompoundName(shifrPre);

        model.addAttribute("nameuni",s);
        model.addAttribute("header","Список показателей");
        s= Util.getCurrentDate();

        model.addAttribute("nametable",s);

        viewName="ratingPokazReport";
        modelAndView = new ModelAndView(viewName, model);

        return modelAndView;
    }
    @RequestMapping(value="/reppokazs/{shifrpre}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<PokazEntityDTO> getRepPokazList(@PathVariable long shifrpre) {
        List<PokazEntityDTO> pokazList;
        int y=Util.getCurrentYear();
        pokazList=podrService.getPokazAllForPre((int) shifrpre,y);
        logger.debug("Try load pokazList. Amnt of recs= "+pokazList.size());
        return pokazList;
    }
    @RequestMapping(value="/reprating/{shifrpre}/{yfrom}/{yto}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  List<RatingNprRecDTO> getRepRatingNprList(@PathVariable long shifrpre,
                                                      @PathVariable int yfrom,
                                                      @PathVariable int yto) {
        List<RatingNprRecDTO> ratingList;
//       int y=Util.getCurrentYear();
        ratingList=podrService.getRatingAllForPre((int) shifrpre,yfrom,yto);
        logger.debug("Try load ratingList. Amnt of recs= "+ratingList.size());
        return ratingList;
    }

    @RequestMapping(value="/diapokcolumn/{shifrpre}/{yfrom}/{yto}/{shifrnpr}/{shifrdet}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public  PokazBarByYearDTO getPokazBarByYearForPre(@PathVariable long shifrpre,
                                                      @PathVariable int yfrom,
                                                      @PathVariable int yto,
                                                      @PathVariable int shifrnpr,
                                                      @PathVariable int shifrdet) {
        PokazBarByYearDTO pb;
//       int y=Util.getCurrentYear();
        pb=podrService.getPokazBarByYearForPre((int) shifrpre,yfrom,yto,shifrnpr,shifrdet);
        logger.debug("Try load diapokcolumn. ShifrPre="+shifrpre+" yfrom="+yfrom+" yto="+yto+" shifrnpr="+shifrnpr+" shifrdet="+shifrdet);
        return pb;
    }

}
