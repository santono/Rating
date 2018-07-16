package rating.domain;


public class WidExplorerEntity {
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

        WidExplorerEntity that = (WidExplorerEntity) o;

        if (id != that.id) return false;
        if (!(name.equals(that.name))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (int) id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("WidExplorerEntity id=" + id);
        sb.append(", name="   + (name != null ? name : ""));
        retVal = sb.toString();
        return retVal;
    }

}
