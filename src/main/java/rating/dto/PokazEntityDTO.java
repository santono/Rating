package rating.dto;


public class PokazEntityDTO {

    private String name;
    private int lineno;
    private double amnt;
    private double amntn;
    private int countntr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmnt() {
        return amnt;
    }

    public void setAmnt(double amnt) {
        this.amnt = amnt;
    }
    public double getAmntn() {
        return amntn;
    }

    public void setAmntn(double amntn) {
        this.amntn = amntn;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }

    public int getCountntr() {
        return countntr;
    }

    public void setCountntr(int countntr) {
        this.countntr = countntr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PokazEntityDTO that = (PokazEntityDTO) o;

        if (lineno != that.lineno) return false;
        if (Math.abs(amnt-that.amnt)>0.1) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long result;
        result = (int) amnt;
        result = 31 * result + lineno;
        result = 31*result + (name != null ? name.hashCode() : 0);
        return (int )result;
    }
}
