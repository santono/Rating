package rating.domain;


public class PokazEntity {
    private static final long serialVersionUID = -5727566243002896041L;

    private int id;
    private String name;
    private String shortname;
    private int idowner;
    private int lineno;

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

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public int getIdowner() {
        return idowner;
    }

    public void setIdowner(int idowner) {
        this.idowner = idowner;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokazEntity that = (PokazEntity) o;

        if (id != that.id) return false;
        if (lineno != that.lineno) return false;
        if (idowner != that.idowner) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (shortname != null ? !shortname.equals(that.shortname) : that.shortname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long result = (long) id;
        result = 31 * result + lineno;
        result = 31 * result + idowner;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortname != null ? shortname.hashCode() : 0);
        return (int )result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("PokazEntity id=" + id);
        sb.append(", name=" + (name != null ? name : ""));
        sb.append(", shortname=" + (shortname != null ? shortname : ""));
        sb.append(", lineno=" + lineno);
        sb.append(", idowner=" + idowner);
        retVal = sb.toString();
        return retVal;
    }

}
