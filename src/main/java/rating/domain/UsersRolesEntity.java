package rating.domain;


import java.io.Serializable;

public class UsersRolesEntity implements Serializable {
    private static final long serialVersionUID = -5527566248002276042L;

    private int id;
    private int shifrIdUser;
    private int shifrIdRole;

    public UsersRolesEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShifrIdUser() {
        return shifrIdUser;
    }

    public void setShifrIdUser(int shifrIdUser) {
        this.shifrIdUser = shifrIdUser;
    }

    public int getShifrIdRole() {
        return shifrIdRole;
    }

    public void setShifrIdRole(int shifrIdRole) {
        this.shifrIdRole = shifrIdRole;
    }
}
