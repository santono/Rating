package rating.domain;

import java.io.Serializable;

/**
 * Таблица подразделений.
 */
public class PodrEntity implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;
    private long id;
    private String name;
    private String shortName;
    private long shifrIdOwner;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public long getShifrIdOwner() {
        return shifrIdOwner;
    }

    public void setShifrIdOwner(long shifrIdOwner) {
        this.shifrIdOwner = shifrIdOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PodrEntity that = (PodrEntity) o;

        if (id != that.id) return false;
        if (shifrIdOwner != that.shifrIdOwner) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (int) shifrIdOwner;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("PodrEntity id=" + id);
        sb.append(", name=" + (name != null ? name : ""));
        sb.append(", shortName=" + (shortName != null ? shortName : ""));
        sb.append(", shifrIdOwner=" + shifrIdOwner);
        retVal = sb.toString();
        return retVal;
    }

}

