package rating.domain;


public class RoadPointEntity {
    private int id;
    private int idroad;
    private int lineno;
    private double xx;
    private double yy;
    private String balloon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdroad() {
        return idroad;
    }

    public void setIdroad(int idroad) {
        this.idroad = idroad;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }

    public double getXx() {
        return xx;
    }

    public void setXx(double xx) {
        this.xx = xx;
    }

    public double getYy() {
        return yy;
    }

    public void setYy(double yy) {
        this.yy = yy;
    }

    public String getBalloon() {
        return balloon;
    }

    public void setBalloon(String balloon) {
        this.balloon = balloon;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + idroad;
        result = 31 * result + lineno;
        result = 31 * result + (int) xx;
        result = 31 * result + (int) yy;
        result = 31 * result + (balloon != null ? balloon.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("RoadEntity id=" + id);
        sb.append(", idroad=" + idroad);
        sb.append(", lineno=" + lineno);
        sb.append(", balloon=" + (balloon != null ? balloon : ""));
        sb.append(", xx=" + xx);
        sb.append(", yy=" + yy);
        retVal = sb.toString();
        return retVal;
    }
}
