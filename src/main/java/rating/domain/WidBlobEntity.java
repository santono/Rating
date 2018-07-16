package rating.domain;


import java.io.Serializable;

public class WidBlobEntity implements Serializable {
    private static final long serialVersionUID = -5723563248002296042L;
    private int id;
    private String name;
	private String mime;

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

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WidBlobEntity that = (WidBlobEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mime != null ? !mime.equals(that.mime) : that.mime != null) return false;
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
