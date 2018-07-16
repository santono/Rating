package rating.domain;


import java.io.Serializable;


public class RoadEntity implements Serializable {
    private static final long serialVersionUID = -5347563448704476079L;

    private int id;
    private int kod;
    private String code;
    private String coderus;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCoderus() {
        return coderus;
    }

    public void setCoderus(String code_rus) {
        this.coderus = code_rus;
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

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + kod;
        result = 31 * result + (int) dlina;
        result = 31 * result + kodtype;
        result = 31 * result + shifridwr;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (coderus != null ? coderus.hashCode() : 0);
        result = 31 * result + (index != null ? index.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("road id=" + id);
        sb.append(", kod=" + kod);
        sb.append(", name=" + name);
        sb.append(", code=" + code);
        sb.append(", coderus=" + coderus);
        sb.append(", kodtype=" + kodtype);
        sb.append(", shifridwr=" + shifridwr);
        sb.append(", index=" + index);
        return sb.toString();
    }


}
