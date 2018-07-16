package rating.dto;


import java.io.Serializable;

public class RoadRecDTO implements Serializable {
    private static final long serialVersionUID = -5527566248001296041L;
    private int id;
    private int kod;
    private String menur;
    private String code;
    private String name;
    private double dlina;
    private int kodtype;
    private int shifridwr;
    private String index;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKod() {
        return kod;
    }

    public void setKod(int kod) {
        this.kod = kod;
    }

    public String getMenur() {
        return menur;
    }

    public void setMenur(String menur) {
        this.menur = menur;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDlina() {
        return dlina;
    }

    public void setDlina(double dlina) {
        this.dlina = dlina;
    }

    public int getKodtype() {
        return kodtype;
    }

    public void setKodtype(int kodtype) {
        this.kodtype = kodtype;
    }

    public int getShifridwr() {
        return shifridwr;
    }

    public void setShifridwr(int shifridwr) {
        this.shifridwr = shifridwr;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
