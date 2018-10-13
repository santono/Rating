package rating.dto;


import java.util.List;

public class PokazBarByYearDTO {
    private String title;
    private String subtitle;
    private List<String> categories;
    private List<ColumnDiagramSerieDTO> series;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<ColumnDiagramSerieDTO> getSeries() {
        return series;
    }

    public void setSeries(List<ColumnDiagramSerieDTO> series) {
        this.series = series;
    }
}
