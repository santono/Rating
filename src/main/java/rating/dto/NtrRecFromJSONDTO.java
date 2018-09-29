package rating.dto;


import java.util.GregorianCalendar;

public class NtrRecFromJSONDTO {
    private int id;
    private String name;
    private String parametry;
    private GregorianCalendar datePublJava;
    private String datepubl;
    private String approved;
    private String dataapproved;
    private String fioapproved;
    private int amntOfImages;
    private int amntOfDocs;
    private int shifrPre;
    private int idRinc;
    private String hrefRinc;


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

    public String getParametry() {
        return parametry;
    }

    public void setParametry(String parametry) {
        this.parametry = parametry;
    }

    public GregorianCalendar getDatePublJava() {
        return datePublJava;
    }

    public void setDatePublJava(GregorianCalendar datePublJava) {
        this.datePublJava = datePublJava;
    }

    public String getDatepubl() {
        return datepubl;
    }

    public void setDatepubl(String datepubl) {
        this.datepubl = datepubl;
    }

    public int getAmntOfImages() {
        return amntOfImages;
    }

    public void setAmntOfImages(int amntOfImages) {
        this.amntOfImages = amntOfImages;
    }

    public int getAmntOfDocs() {
        return amntOfDocs;
    }

    public void setAmntOfDocs(int amntOfDocs) {
        this.amntOfDocs = amntOfDocs;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getDataapproved() {
        return dataapproved;
    }

    public void setDataapproved(String dataapproved) {
        this.dataapproved = dataapproved;
    }

    public String getFioapproved() {
        return fioapproved;
    }

    public void setFioapproved(String fioapproved) {
        this.fioapproved = fioapproved;
    }

    public int getShifrPre() {
        return shifrPre;
    }

    public void setShifrPre(int shifrPre) {
        this.shifrPre = shifrPre;
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
}
