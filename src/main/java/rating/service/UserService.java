package rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rating.dao.UserDAO;
import rating.domain.DolgEntity;
import rating.domain.UserEntity;
import rating.domain.UsersRolesEntity;
import rating.dto.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PodrService podrService;
    @Autowired
    private DolgService dolgService;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private UsersRolesService userRolesService;
    
    public UserEntity getById(final int id) {
           return userDAO.getById(id);
    }

    public UserEntity getByLogin(final String login) {
           return userDAO.getByLogin(login);
    }

    public int getCountByLogin(final String login) {
        return userDAO.getCountByLogin(login);    }

    public void saveRecord(UserEntity uEntity) {
        if ((uEntity.getName()==null) || (uEntity.getName().trim().length()==0)) {
            uEntity.setName(uEntity.getFam()!=null?uEntity.getFam().trim():""+" "+uEntity.getNam()!=null?uEntity.getNam().trim():""+" "+uEntity.getOtc()!=null?uEntity.getOtc().trim():"");
            uEntity.setName(uEntity.getName().trim());
        }
        userDAO.saveRecord(uEntity);
    }

    public void deleteRecord(final int wantedId) {
        userRolesService.deleteRecordsForUser(wantedId);
        userDAO.deleteRecord(wantedId);
    }

    public List<UserEntity> getAll() {
        return userDAO.getAll();
    }
    public List<UserEntity> getAllForUser(String whereStmnt) {
        return userDAO.getAllForUser(whereStmnt);
    }
    public String getNameById(final int id) {
        String retval;
        UserEntity uEntity;
        if (id<1) retval="";
        else {
            uEntity=getById(id);
            if (uEntity == null) retval="";
            else {
                retval=uEntity.getFam().trim()+" "+uEntity.getNam().trim()+" "+uEntity.getOtc().trim();
            }
        }
        
        return retval;
    }
    

    public List<UserNPRDTOForVUEList> getAllDTOForUniFacKaf(int shifruni,int shifrfac,int shifrkaf) {
        List <UserNPRDTOForVUEList> uList=new ArrayList<UserNPRDTOForVUEList>();
        List<UserEntity> ueList = userDAO.getAllForUniFacKaf(shifruni, shifrfac, shifrkaf);
        for (UserEntity u:ueList) {
            UserNPRDTOForVUEList uVue = new UserNPRDTOForVUEList();
            uVue.setId(u.getId());


            uVue.setDataVerification(u.getDataVerification());
            uVue.setFIO(u.getFam().trim()+" "+u.getNam().trim()+" "+u.getOtc().trim());
            uVue.setNamePodr("");
            uVue.setShortNamePodr("");;
            uVue.setNameDol("");
            uVue.setStatusName("");
            uList.add(uVue);
        }
        return uList;
    }

    public int getCountUserForPodr(int shifrPodr) {
               return userDAO.getCountUserForPodr(shifrPodr);

    }
    public List<UserNPRDTOForVUEList> getAllDTOForPodr(int shifrPodr,int order) {
           return getPageDTOForPodr(shifrPodr,0,0,order);
    }
    public String getPredpNameForOwner(UserEntity u,int shifrOwner,boolean isFullNameMode) {
        String retVal="";
        StringBuilder sb=new StringBuilder();
        int shifrUni,shifrFac,shifrKaf;
        shifrUni=u.getShifrUni();


        int shifrUniOwner=-1;
        int shifrFacOwner=-1;
        int shifrKafOwner=-1;
        if (shifrUni>0)
            shifrUniOwner = (int) podrService.getById(shifrUni).getShifrIdOwner();
        shifrFac=u.getShifrFac();
        if (shifrFac>0)
            shifrFacOwner = (int) podrService.getById(shifrFac).getShifrIdOwner();
        shifrKaf=u.getShifrKaf();
        if (shifrKaf>0)
            shifrKafOwner = (int) podrService.getById(shifrKaf).getShifrIdOwner();
        if (shifrUniOwner==shifrOwner) {
            if (isFullNameMode)
                sb.append(podrService.getById(shifrUni).getName());
            else
                sb.append(podrService.getById(shifrUni).getShortName());
            if (shifrFac>0) {
                if (isFullNameMode) 
                   sb.append(" / "+podrService.getById(shifrFac).getName());
                else
                   sb.append("/"+podrService.getById(shifrFac).getShortName());
                
                if (shifrKaf>0) {
                    if (isFullNameMode)
                       sb.append(" / "+podrService.getById(shifrKaf).getName());
                    else
                       sb.append("/"+podrService.getById(shifrKaf).getShortName());
                }
            }
        }
        else
        if (shifrFacOwner==shifrOwner) {
            if (isFullNameMode)
               sb.append(podrService.getById(shifrFac).getName());
            else
               sb.append(podrService.getById(shifrFac).getShortName());
            
            if (shifrKaf>0) {
                if (isFullNameMode)
                   sb.append(" / "+podrService.getById(shifrKaf).getName());
                else
                   sb.append("/"+podrService.getById(shifrKaf).getShortName());
            }
        }
        else
        if (shifrKafOwner==shifrOwner) {
            if (isFullNameMode)
                sb.append(podrService.getById(shifrKaf).getName());
            else
                sb.append(podrService.getById(shifrKaf).getShortName());
        }
        
        retVal=sb.toString();
        return retVal;
    }
    public List<UserNPRDTOForVUEList> getPageDTOForPodr(int shifrPodr,int pageNo, int pageSize,int order) {
        List <UserNPRDTOForVUEList> uList=new ArrayList<UserNPRDTOForVUEList>();
        List<UserEntity> ueList;
        if (pageNo<1) {
           ueList = userDAO.getAllForPodr(shifrPodr);
        } else {
           ueList = userDAO.getPageForPodr(shifrPodr,pageNo,pageSize,order);
        }
        for (UserEntity u:ueList) {
            UserNPRDTOForVUEList uVue = new UserNPRDTOForVUEList();
            uVue.setId(u.getId());
            StringBuilder sb=new StringBuilder("");
            if (u.getFam()!=null)
               sb.append(u.getFam().trim());
            if (u.getNam() != null)
               sb.append(" "+u.getNam().trim());
            if (u.getOtc() != null)
               sb.append(" "+u.getOtc().trim());
            uVue.setFIO(sb.toString());
            uVue.setFIO(uVue.getFIO().trim());
            if (u.getStatusCode()==1) {
               uVue.setDataVerification(u.getDataVerification());
            } else {
                uVue.setDataVerification("");
            }    
//            uVue.setFIO(u.getFam() != null ? u.getFam().trim() : "" + " " + u.getNam() != null ? u.getNam().trim() : "" + " " + u.getOtc() != null ? u.getOtc().trim() : "");
            if (u.getShifrPodr()>0) {
                uVue.setNamePodr(podrService.getById(u.getShifrPodr()).getName());
            } else
            if (u.getShifrKaf()>0) {
                uVue.setNamePodr(podrService.getById(u.getShifrKaf()).getName());
            } else
            if (u.getShifrFac()>0) {
                uVue.setNamePodr(podrService.getById(u.getShifrFac()).getName());
            } else
            if (u.getShifrUni()>0) {
                uVue.setNamePodr(podrService.getById(u.getShifrUni()).getName());
            } else {
              uVue.setNamePodr("");
            }
            String namePredp=getPredpNameForOwner(u,shifrPodr,true);
            uVue.setNamePodr(namePredp);
            String shortNamePredp=getPredpNameForOwner(u,shifrPodr,false);
            uVue.setShortNamePodr(shortNamePredp);
            String nameDol="";
            int shifrDol=0;
            if (u.getShifrDol()>0) {
                shifrDol = u.getShifrDol();
            }
//            System.out.println("shifrdol="+shifrDol);
            if (shifrDol>0) {
                DolgEntity de=dolgService.getById(shifrDol);
                if (de!=null)
                   nameDol=de.getShortname();
            }
//            else
//                nameDol="";
//            System.out.println("namedol="+nameDol);
            uVue.setNameDol(nameDol);
            if (u.getStatusCode()==0)
//                uVue.setStatusName("Не зарегистрирован");
                uVue.setStatusName("");
            else
            if (u.getStatusCode()==1)
                uVue.setStatusName("Зарегистрирован");

            uList.add(uVue);
        }
        return uList;
    }

    public void setVerified(UserEntity uEntity) {
        userDAO.setVerified(uEntity);
    }
    public String sendPasswordForUser(UserEntity uEntity,String servletContextPath) {
           String toAddress;
           toAddress = uEntity.getEmail();
           String pwd;
           pwd       = generateRandomPassword();
           String login;
           login     = uEntity.getLogin();
           String address;
           address   = servletContextPath;
           boolean result;
           String retVal;
//           System.out.println("pwd 1="+pwd);
           result=mailSenderService.sendPasswordByMail(toAddress,pwd,login,address);
           if (result) {
               uEntity.setPassword(pwd);
               saveRecord(uEntity);
               retVal=pwd;
           } else {
               retVal="";
           }
           return retVal;
    }
    public void setPassword(ChgPwdDTO up) {
        userDAO.setPassword(up);
    }

    public SemanticUIDropDownDTO getSourceForSemanticUIDropDown(String query) {
        SemanticUIDropDownDTO suddd=new SemanticUIDropDownDTO();
        List<SemanticUIDropDownItemDTO> sList=new ArrayList<SemanticUIDropDownItemDTO>();
        suddd.setResults(sList);
        suddd.setSuccess(false);
        List<UserEntity> userList=userDAO.getAllForQuery(query);
        if (userList!=null)
            if (userList.size()>0) {
                for (UserEntity u:userList) {
                    UserNPRDTOForVUEList uVue = new UserNPRDTOForVUEList();
                    uVue.setId(u.getId());
                    SemanticUIDropDownItemDTO item=new SemanticUIDropDownItemDTO();
                    item.setValue((int)u.getId());
                    StringBuilder sb=new StringBuilder("");
                    if (u.getFam()!=null)
                        sb.append(u.getFam().trim());
                    if (u.getNam() != null)
                        sb.append(" "+u.getNam().trim());
                    if (u.getOtc() != null)
                        sb.append(" "+u.getOtc().trim());
                    item.setName(sb.toString());
                    item.setName(item.getName().trim());
                    item.setText(item.getName().trim());
                    item.setDisabled(false);
                    sList.add(item);
                }
                suddd.setResults(sList);
                suddd.setSuccess(true);
            }

        return suddd;
    }
    
    public String getCompoundName(int id)  {
        UserEntity u=getById(id);
        String retval;
        if (u==null) {
            retval="";
        } else {
            StringBuilder sb=new StringBuilder();
            if (u.getFam()!=null)
                sb.append(u.getFam().trim());
            if (u.getNam() != null)
            sb.append(" "+u.getNam().trim());
            if (u.getOtc() != null)
               sb.append(" "+u.getOtc().trim());
            retval=sb.toString();
        }
        return retval;
    }
    public String getShortCompoundName(int id)  {
        UserEntity u=getById(id);
        String retval;
        if (u==null) {
            retval="";
        } else {
            StringBuilder sb=new StringBuilder();
            if (u.getFam()!=null)
                sb.append(u.getFam().trim());
            if (u.getNam() != null)
                sb.append(" "+u.getNam().trim().charAt(0)+".");
            if (u.getOtc() != null)
                sb.append(" "+u.getOtc().trim().charAt(0)+".");
            retval=sb.toString();
        }
        return retval;
    }
    
    public void MakePPSRoleForNewUser(UserEntity userEntity) {
        List<UsersRolesEntity> lr;
        if (userEntity.getId()<1)
           return;
        lr = userRolesService.getAllForUser((int)userEntity.getId());
        if (lr.size()>0)
            return;
        UsersRolesEntity role;
        role=new UsersRolesEntity();
        role.setShifrIdUser((int) userEntity.getId());
        role.setShifrIdRole(RolesEnum.NPR.getRoleCode());
        userRolesService.saveRecord(role);
    }
    
    public String generateRandomPassword() {
//        int PasswordLength = (int)(Math.random()*15)+5;
        int PasswordLength = 6;
//        int CharacterCapCount = (int)(Math.random()*(PasswordLength+1))+2;
        int CharacterCapCount = 2;
//        int NumberCount = 5 >= 5 ? (int)(Math.random()*(6)):(int)(Math.random()*(PasswordLength-CharacterCapCount));
        int NumberCount = 2;

        int CharacterCount = PasswordLength - CharacterCapCount - NumberCount;
        ArrayList<Character> password = new ArrayList<Character>();
        Random r = new Random();
//        password.add('_');
        for (int i = 0; i < CharacterCapCount; i++)
        {
            password.add((char)((int)(Math.random()*25)+65));
        }
        for (int i = 0; i < NumberCount; i++)
        {
            password.add((char)((int)(Math.random()*10)+48));
        }
        for (int i = 0; i < CharacterCount; i++)
        {
            password.add((char)((int)(Math.random()*26)+97));
        }
        Collections.shuffle(password);
        String str = password.toString().replaceAll(", |\\[|\\]", "");
        while (str.matches("(.*)\\d{2}(.*)"))
        {
            Collections.shuffle(password);
            str = password.toString().replaceAll(", |\\[|\\]", "");
        }
//        System.out.println(str);
        return str;
    }

    public boolean checkLogin(String login) {
        boolean retVal=true;
        retVal=userDAO.checkLogin(login);
        return retVal;
    }

}
