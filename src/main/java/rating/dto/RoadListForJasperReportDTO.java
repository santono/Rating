package rating.dto;



import java.io.Serializable;

public class RoadListForJasperReportDTO implements Serializable,Comparable<RoadListForJasperReportDTO> {
    private int lineno;
    private String name;
    private String code;
    private double dlina;

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDlina() {
        return dlina;
    }

    public void setDlina(double dlina) {
        this.dlina = dlina;
    }
    @Override
    public int compareTo(RoadListForJasperReportDTO o) {
        int retVal=0;
        if (getLineno()>o.getLineno()) {
            retVal=1;
        } else if (getLineno()<o.getLineno()) {
            retVal=-1;
        } else {
            retVal=0;
        }
        return retVal;
    }

}
