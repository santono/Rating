package rating.dto;


import java.util.List;

public class UserDTOForVUE {
    private long id;
    private String FIO;
    private long shifrPodr;
    private int tabno;
    private String namePodr;
    List<RoleDTO> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public long getShifrPodr() {
        return shifrPodr;
    }

    public void setShifrPodr(long shifrPodr) {
        this.shifrPodr = shifrPodr;
    }

    public int getTabno() {
        return tabno;
    }

    public void setTabno(int tabno) {
        this.tabno = tabno;
    }

    public String getNamePodr() {
        return namePodr;
    }

    public void setNamePodr(String namePodr) {
        this.namePodr = namePodr;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
