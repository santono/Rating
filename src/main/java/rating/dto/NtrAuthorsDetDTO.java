package rating.dto;


public class NtrAuthorsDetDTO {
    private int id;
    private String name;
    private int amode;
    private int procent;

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

    public int getAmode() {
        return amode;
    }

    public void setAmode(int amode) {
        this.amode = amode;
    }

    public int getProcent() {
        return procent;
    }

    public void setProcent(int procent) {
        this.procent = procent;
    }
}
