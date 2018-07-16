package rating.domain;

public class CityEntity {
    private int id;
    private int kod;
    private String name;
    private String ymapl;
    private String xx;
    private String yy;
    private String balloon;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYmapl() {
        return ymapl;
    }

    public void setYmapl(String ymapl) {
        this.ymapl = ymapl;
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("cityEntity id=" + id);
        sb.append(", name=" + name);
        return sb.toString();
    }
}
