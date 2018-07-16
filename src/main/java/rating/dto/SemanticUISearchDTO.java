package rating.dto;

import java.util.ArrayList;
import java.util.List;

public class SemanticUISearchDTO {
    private int totalCount;
    private boolean incompleteSearch;
    private List<SemanticUISearchItemDTO> items;
    public SemanticUISearchDTO() {
    }
    public SemanticUISearchDTO(boolean empty) {
        this.totalCount = 0;
        this.incompleteSearch = false;
        items=new ArrayList<SemanticUISearchItemDTO>();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncompleteSearch() {
        return incompleteSearch;
    }

    public void setIncompleteSearch(boolean incompleteSearch) {
        this.incompleteSearch = incompleteSearch;
    }

    public List<SemanticUISearchItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SemanticUISearchItemDTO> items) {
        this.items = items;
    }
}
