package rating.dto;

public class ItemXXYYDTO {
    private String xx;
    private String yy;
    private String balloon;
    private int lineno;


    public ItemXXYYDTO(int lineno, String xx, String yy, String balloon) {
        this.lineno = lineno;
        this.xx = xx;
        this.yy = yy;
        this.balloon = balloon;
    }

    public String getXx() {
        return xx;
    }

    public void setXx(String xx) {
        this.xx = xx;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public String getBalloon() {
        return balloon;
    }

    public void setBalloon(String balloon) {
        this.balloon = balloon;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }
}
