package rating.util.forbsgridcontroller;

import java.io.Serializable;

public class SortingClass implements Serializable {
    private String field;
    private String order;
    private String sortingName;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSortingName() {
        return sortingName;
    }

    public void setSortingName(String sortingName) {
        this.sortingName = sortingName;
    }
}
