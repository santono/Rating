package rating.domain;


import java.io.Serializable;

public class UStepEntity  implements Serializable {
    private static final long serialVersionUID = -5527566248002196041L;

    private int id;
    private String name;
    private String shortName;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

        UStepEntity that = (UStepEntity) o;

        if (id != that.id) return false;
        if (kind != that.kind) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long result = (long) id;
        result = 31 * result + kind;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        return (int )result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("UStepEntity id=" + id);
        sb.append(", name=" + (name != null ? name : ""));
        sb.append(", shortName=" + (shortName != null ? shortName : ""));
        sb.append(", kind=" + kind);
        retVal = sb.toString();
        return retVal;
    }

}
