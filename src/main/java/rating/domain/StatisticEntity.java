package rating.domain;


import java.io.Serializable;
import java.util.Date;

public class StatisticEntity implements Serializable {
    private static final long serialVersionUID = -5529566248002276042L;
    private int id;
    private String ip;
    private int shifrwrk;
    private Date datewrkin;
    private Date datewrkout;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatewrkout() {
        return datewrkout;
    }

    public void setDatewrkout(Date datewrkout) {
        this.datewrkout = datewrkout;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getShifrwrk() {
        return shifrwrk;
    }

    public void setShifrwrk(int shifrwrk) {
        this.shifrwrk = shifrwrk;
    }

    public Date getDatewrkin() {
        return datewrkin;
    }

    public void setDatewrkin(Date datewrkin) {
        this.datewrkin = datewrkin;
    }
}
