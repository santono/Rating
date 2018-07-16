package rating.domain;

import java.io.Serializable;


public class RoleEntity implements Serializable {
    private static final long serialVersionUID = -5527566248002296042L;
    private long id;
    private String name;
    private String description;

    public RoleEntity() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
