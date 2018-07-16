package rating.grid;

import rating.dto.RoadRecDTO;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class RoadGrid implements Serializable {
    private String total_rows;
    private List<RoadRecDTO> page_data;
    private Object error;
    private String[] filter_error;
    private String[] debug_message;


    public String getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(String total_rows) {
        this.total_rows = total_rows;
    }

    public List<RoadRecDTO> getPage_data() {
        return page_data;
    }

    public void setPage_data(List<RoadRecDTO> page_data) {
        this.page_data = page_data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public String[] getFilter_error() {
        return filter_error;
    }

    public void setFilter_error(String[] filter_error) {
        this.filter_error = filter_error;
    }

    public String[] getDebug_message() {
        return debug_message;
    }

    public void setDebug_message(String[] debug_message) {
        this.debug_message = debug_message;
    }
}
