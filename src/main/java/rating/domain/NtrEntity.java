package rating.domain;


import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NtrEntity  implements Serializable {
    private static final long serialVersionUID = -5927566847002896041L;

    private int id;
    private String name;
    private Calendar datepubl;
    private int status; 
    private Calendar dateapprove;
    private int shifrwrkapprove; 
    private String fioapprove; 
    private int shifrpre; 
    private int shifrwrk; 
    private String fiowrk; 
    private Calendar datewrk;
    private String parametry;
    private int idRinc;
    private String hrefRinc;
    private int yPubl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDatepubl() {
        return datepubl;
    }

    public void setDatepubl(Calendar datepubl) {
        this.datepubl = datepubl;
    }
    public void setDatepubl(java.sql.Date datepubl) {
        this.datepubl = new GregorianCalendar();
        this.datepubl.setTime(datepubl);
//        this.dateUpload = dateUpload;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Calendar getDateapprove() {
        return dateapprove;
    }

    public void setDateapprove(Calendar dateapprove) {
        this.dateapprove = dateapprove;
    }
    public void setDateapprove(java.sql.Date dateapprove) {
        this.dateapprove = new GregorianCalendar();
        this.dateapprove.setTime(dateapprove);
//        this.dateUpload = dateUpload;
    }

    public int getShifrwrkapprove() {
        return shifrwrkapprove;
    }

    public void setShifrwrkapprove(int shifrwrkapprove) {
        this.shifrwrkapprove = shifrwrkapprove;
    }

    public String getFioapprove() {
        return fioapprove;
    }

    public void setFioapprove(String fioapprove) {
        this.fioapprove = fioapprove;
    }

    public int getShifrpre() {
        return shifrpre;
    }

    public void setShifrpre(int shifrpre) {
        this.shifrpre = shifrpre;
    }

    public int getShifrwrk() {
        return shifrwrk;
    }

    public void setShifrwrk(int shifrwrk) {
        this.shifrwrk = shifrwrk;
    }

    public String getFiowrk() {
        return fiowrk;
    }

    public void setFiowrk(String fiowrk) {
        this.fiowrk = fiowrk;
    }

    public Calendar  getDatewrk() {
        return datewrk;
    }

    public void setDatewrk(Calendar  datewrk) {
        this.datewrk = datewrk;
    }

    public void setDatewrk(java.sql.Date datewrk) {
        this.datewrk = new GregorianCalendar();
        this.datewrk.setTime(datewrk);
//        this.dateUpload = dateUpload;
    }

    public String getParametry() {
        return parametry;
    }

    public void setParametry(String parametry) {
        this.parametry = parametry;
    }

    public int getIdRinc() {
        return idRinc;
    }

    public void setIdRinc(int idRinc) {
        this.idRinc = idRinc;
    }

    public String getHrefRinc() {
        return hrefRinc;
    }

    public void setHrefRinc(String hrefRinc) {
        this.hrefRinc = hrefRinc;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NtrEntity that = (NtrEntity) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (shifrpre != that.shifrpre) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parametry != null ? !parametry.equals(that.parametry) : that.parametry != null) return false;

        return true;
    }

    public int getyPubl() {
        return yPubl;
    }

    public void setyPubl(int yPubl) {
        this.yPubl = yPubl;
    }

    @Override
    public int hashCode() {
        long result = (long) id;
        result = 31 * result + status;
        result = 31 * shifrpre + shifrpre;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return (int )result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("NprEntity id=" + id);
        sb.append(", name=" + (name != null ? name : ""));
        sb.append(", status=" + status);
        retVal = sb.toString();
        return retVal;
    }

}
