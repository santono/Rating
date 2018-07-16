package rating.dto;


public class PodrEntityDTO {
    private long   id;
    private String name;
    private String shortName;
    private long shifrIdOwner;
    private boolean canbedeleted;
    private int level;

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

    public boolean isCanbedeleted() {
        return canbedeleted;
    }

    public void setCanbedeleted(boolean canbedeleted) {
        this.canbedeleted = canbedeleted;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
