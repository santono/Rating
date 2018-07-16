package rating.domain;

public class DolgEntity {
    private static final long serialVersionUID = -5727436243002896041L;

    private int id;
    private String name;
    private String shortname;
    private int kind;

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

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DolgEntity that = (DolgEntity) o;

        if (id   != that.id) return false;
        if (kind != that.kind) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (shortname != null ? !shortname.equals(that.shortname) : that.shortname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long result = (long) id;
        result = 31 * result + kind;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortname != null ? shortname.hashCode() : 0);
        return (int )result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("DolgEntity id=" + id);
        sb.append(", name=" + (name != null ? name : ""));
        sb.append(", shortname=" + (shortname != null ? shortname : ""));
        sb.append(", kind=" + kind);
        retVal = sb.toString();
        return retVal;
    }

}
