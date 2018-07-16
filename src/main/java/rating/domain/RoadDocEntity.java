package rating.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Таблица загруженных документов.
 */
public class RoadDocEntity implements Serializable {
    private static final long serialVersionUID = -5527564246002276042L;

    private long id;
    private long idroad;
    private long iddet;
	private long idwidblob;
    private String comment;
    private String filename;
    private Calendar dateUpload;
    private int shifrwrk;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdroad() {
        return idroad;
    }

    public void setIdroad(long idroad) {
        this.idroad = idroad;
    }

    public long getIddet() {
        return iddet;
    }
    public void setIddet(long iddet) {
        this.iddet = iddet;
    }

    public void setIdwidblob(long idwidblob) {
        this.idwidblob = idwidblob;
    }
    public long getIdwidblob() {
        return idwidblob;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public Calendar getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Calendar dateUpload) {
        this.dateUpload = dateUpload;
    }
    public void setDateUpload(java.sql.Date dateUpload) {
        this.dateUpload = new GregorianCalendar();
        this.dateUpload.setTime(dateUpload);
//        this.dateUpload = dateUpload;
    }

    public int getShifrwrk() {
        return shifrwrk;
    }

    public void setShifrwrk(int shifrwrk) {
        this.shifrwrk = shifrwrk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadDocEntity that = (RoadDocEntity) o;

        if (id != that.id) return false;
        if (idroad != that.idroad) return false;
        if (iddet != that.iddet) return false;
        if (idwidblob != that.idwidblob) return false;
        if (!(filename.equals(that.filename))) return false;
        if (!(comment.equals(that.comment))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (int) idroad;
        result = 31 * result + (int) iddet;
        result = 31 * result + (int) idwidblob;
        result = 31 * result + (int) shifrwrk;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
//        result = 31 * result + (int) Math.round(posstart);
//        result = 31 * result + (int) Math.round(posend);
        return result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("RoadDocEntity id=" + id);
        sb.append(", comment="   + (comment != null ? comment : ""));
        sb.append(", idroad="    + idroad);
        sb.append(", iddet="     + iddet);
        sb.append(", idwidblob=" + idwidblob);
        retVal = sb.toString();
        return retVal;
    }



}

