package rating.dto;

import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;
import java.util.Locale;


public class UserNPRDTOForVUEList {
    private long id;
    private String FIO;
    private String namePodr;
    private String shortNamePodr;
    private String nameDol;
    private String statusName;
    private String dataVerification;
    private String canbedeleted;


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

    public String getNamePodr() {
        return namePodr;
    }

    public void setNamePodr(String namePodr) {
        this.namePodr = namePodr;
    }

    public String getShortNamePodr() {
        return shortNamePodr;
    }

    public void setShortNamePodr(String shortNamePodr) {
        this.shortNamePodr = shortNamePodr;
    }

    public String getNameDol() {
        return nameDol;
    }

    public void setNameDol(String nameDol) {
        this.nameDol = nameDol;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDataVerification() {
        return dataVerification;
    }

    public void setDataVerification(String dataVerification) {
        this.dataVerification = dataVerification;
    }
    public void setDataVerification(Date dataVerification) {
        if (dataVerification!=null) {
           SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
           this.dataVerification = dateFormat.format(dataVerification);
        } else
            this.dataVerification="";
    }

    public String getCanbedeleted() {
        return canbedeleted;
    }

    public void setCanbedeleted(String canbedeleted) {
        this.canbedeleted = canbedeleted;
    }
}
