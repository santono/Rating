package rating.domain;


public class RoadCityEntity {
    public int id;
    public int idroad;
    public int idcity;
    public int lineno;

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

    public int getIdcity() {
        return idcity;
    }

    public void setIdcity(int idcity) {
        this.idcity = idcity;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("roadcity id=" + id);
        sb.append(", idroad=" + idroad);
        sb.append(", idcity=" + idcity);
        sb.append(", lineno=" + lineno);
        return sb.toString();
    }
}
