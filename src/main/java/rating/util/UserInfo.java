package rating.util;


import org.springframework.security.core.GrantedAuthority;
import rating.dto.UserDTO;
import rating.dto.UserDTOService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.Collection;

public class UserInfo implements Serializable {
    private UserDTO user;

    public UserInfo() {
        UserDTOService uDTOService = (UserDTOService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = uDTOService.getUserDTO();
    }


    public long getShifrWrk() {
        return user.getId();
    }

    public String getFIO() {
        return user.getFIO();
    }

    public UserDTO getUserDTO() {
        return user;
    }
    public boolean isAdmin() {
        return isRole("ADMIN");
    }
    public boolean isDataAdmin() {
        return isRole("ADDATA");
    }
    public boolean isNPR() {
        return isRole("NPR");
    }
    public boolean isCOMISSION() {     //Комиссия по верификации КФУ
        return isRole("COMISSION");
    }
    public boolean isDEP() {           //Экспертные группы
        return isRole("DEP");
    }
    public boolean isSUP() {           //Руководители
        return isRole("SUP");
    }
    public boolean isDOD() {           //Упономоченные за занеcение данных
        return isRole("DOD");
    }
    public boolean isDNID() {           //Упономоченные за занеcение данных
        return isRole("DNID");
    }
    public boolean isRole(String mask) {
        boolean retVal;
        retVal=false;
        UserDTOService uDTOService = (UserDTOService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> aList =((UserDTOService) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
        for (GrantedAuthority a:aList) {
            if (a.getAuthority().toUpperCase().trim().contains(mask)) {
                retVal=true;
                break;
            }
        }
        return retVal;

    }

//    public TableInfo getTableInfo() {
//        return tableInfo;
//    }
//
//    public void setTableInfo(TableInfo tableInfo) {
//        this.tableInfo = tableInfo;
//    }
}
