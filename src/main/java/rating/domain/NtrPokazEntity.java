package rating.domain;


public class NtrPokazEntity {
    private static final long serialVersionUID = -5327567345003206041L;

    private int id;
    private int idpokaz;
    private int idntr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpokaz() {
        return idpokaz;
    }

    public void setIdpokaz(int idpokaz) {
        this.idpokaz = idpokaz;
    }

    public int getIdntr() {
        return idntr;
    }

    public void setIdntr(int idntr) {
        this.idntr = idntr;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NtrPokazEntity that = (NtrPokazEntity) o;

        if (id != that.id) return false;
        if (idpokaz != that.idpokaz) return false;
        if (idntr != that.idntr) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (int) idpokaz;
        result = 31 * result + (int) idntr;
        return result;
    }

    @Override
    public String toString() {
        String retVal;
        StringBuilder sb = new StringBuilder();
        sb.append("NtrPokazEntity id=" + id);
        sb.append(", idntr=" + idntr);
        sb.append(", idpokaz=" + idpokaz);
        retVal = sb.toString();
        return retVal;
    }

}
