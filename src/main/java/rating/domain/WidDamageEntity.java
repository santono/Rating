package rating.domain;


import java.io.Serializable;

public class WidDamageEntity implements Serializable {
    private static final long serialVersionUID = -5727563248002296042L;
    private int id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WidDamageEntity that = (WidDamageEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("WidDamageEntity id=" + id);
        sb.append(", name=" + (name != null ? name : ""));
        retVal = sb.toString();
        return retVal;
    }


}
