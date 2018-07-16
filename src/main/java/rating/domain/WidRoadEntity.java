package rating.domain;

import java.io.Serializable;

/**
 * Таблица подразделений.
 */
public class WidRoadEntity implements Serializable {
    private static final long serialVersionUID = -5527563248002296042L;
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

        WidRoadEntity that = (WidRoadEntity) o;

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
        sb.append("WidRoadEntity id=" + id);
        sb.append(", name=" + (name != null ? name : ""));
        retVal = sb.toString();
        return retVal;
    }

}

