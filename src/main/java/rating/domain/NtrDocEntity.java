package rating.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Таблица загруженных документов.
 */
public class NtrDocEntity implements Serializable {
    private static final long serialVersionUID = -5572564246332276042L;

    private int id;
    private int idntr;
	private int idwidblob;
    private String comment;
    private String filename;
    private Calendar dateupload;
    private int shifrwrk;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdntr() {
        return idntr;
    }

    public void setIdntr(int idntr) {
        this.idntr = idntr;
    }


    public void setIdwidblob(int idwidblob) {
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


    public Calendar getDateupload() {
        return dateupload;
    }

    public void setDateupload(Calendar dateupload) {
        this.dateupload = dateupload;
    }
    public void setDateupload(java.sql.Date dateupload) {
        this.dateupload = new GregorianCalendar();
        this.dateupload.setTime(dateupload);
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

        NtrDocEntity that = (NtrDocEntity) o;

        if (id != that.id) return false;
        if (idntr != that.idntr) return false;
        if (idwidblob != that.idwidblob) return false;
        if (!(filename.equals(that.filename))) return false;
        if (!(comment.equals(that.comment))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (int) idntr;
        result = 31 * result + (int) idwidblob;
        result = 31 * result + (int) shifrwrk;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("NtrDocEntity id=" + id);
        sb.append(", comment="   + (comment != null ? comment : ""));
        sb.append(", idntr="     + idntr);
        sb.append(", idwidblob=" + idwidblob);
        retVal = sb.toString();
        return retVal;
    }



}

