package rating.dto;

public class RatingNprRecDTO implements Comparable<RatingNprRecDTO>{
    private int    lineno;
    private String fio;
    private int    amnt;
    private double amntProc;
    private String namePre;

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getAmnt() {
        return amnt;
    }

    public void setAmnt(int amnt) {
        this.amnt = amnt;
    }

    public double getAmntProc() {
        return amntProc;
    }

    public void setAmntProc(double amntProc) {
        this.amntProc = amntProc;
    }

    public String getNamePre() {
        return namePre;
    }

    public void setNamePre(String namePrep) {
        this.namePre = namePrep;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingNprRecDTO that = (RatingNprRecDTO) o;

        if (lineno != that.lineno) return false;
        if (Math.abs(amnt-that.amnt)>0.1) return false;
        if (fio != null ? !fio.equals(that.fio) : that.fio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long result;
        result = (int) amnt;
        result = 31 * result + lineno;
        result = 31*result + (fio != null ? fio.hashCode() : 0);
        return (int )result;
    }

    @Override
    public int compareTo(RatingNprRecDTO o) {
        int retVal;
        if (this.lineno>o.getLineno()) retVal=1;
        else
        if (this.lineno<o.getLineno()) retVal=-1;
        else
           retVal = 0;
        return retVal;
    }
}
