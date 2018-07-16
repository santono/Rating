package rating.dto;


import java.io.Serializable;

public class UserDTO implements Serializable {
    private long id;
    private String FIO;
    private long shifrPodr;
    private String login;
    private String password;
    private int tabno;
    private String namePodr;


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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
