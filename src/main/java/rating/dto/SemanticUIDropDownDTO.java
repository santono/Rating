package rating.dto;


import java.util.List;

public class SemanticUIDropDownDTO {
   private boolean success;
   List<SemanticUIDropDownItemDTO> results;
    public SemanticUIDropDownDTO() {

    }
    public SemanticUIDropDownDTO(boolean empty) {
        this.success = false;
        results      = null;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<SemanticUIDropDownItemDTO> getResults() {
        return results;
    }

    public void setResults(List<SemanticUIDropDownItemDTO> results) {
        this.results = results;
    }
}
