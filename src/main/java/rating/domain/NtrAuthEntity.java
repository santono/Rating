package rating.domain;


public class NtrAuthEntity {
    private static final long serialVersionUID = -5327566345002196041L;

    private int id;
    private int idauth;
    private int idntr;
    private int amode;
    private String name;
    private int procent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdauth() {
        return idauth;
    }

    public void setIdauth(int idauth) {
        this.idauth = idauth;
    }

    public int getIdntr() {
        return idntr;
    }

    public void setIdntr(int idntr) {
        this.idntr = idntr;
    }

    public int getAmode() {
        return amode;
    }

    public void setAmode(int amode) {
        this.amode = amode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProcent() {
        return procent;
    }

    public void setProcent(int procent) {
        this.procent = procent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NtrAuthEntity that = (NtrAuthEntity) o;

        if (id      != that.id)      return false;
        if (idauth  != that.idauth)  return false;
        if (idntr   != that.idntr)   return false;
        if (amode   != that.amode)   return false;
        if (procent != that.procent) return false;
        if (name    != null ? !name.equals(that.name) : that.name != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (int) idauth;
        result = 31 * result + (int) idntr;
        result = 31 * result + (int) amode;
        result = 31 * result + (int) procent;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("NtrAuthEntity id=" + id);
        sb.append(", name="    + (name != null ? name : ""));
        sb.append(", idntr="   + idntr);
        sb.append(", idauth="  + idauth);
        sb.append(", amode="   + amode);
        sb.append(", procent=" + procent);
        retVal = sb.toString();
        return retVal;
    }

}
