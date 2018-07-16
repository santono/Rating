package rating.dto;


public class NtrAuthorsDTO {
    private int id;
    NtrAuthorsDetDTO[] authors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NtrAuthorsDetDTO[] getAuthors() {
        return authors;
    }

    public void setAuthors(NtrAuthorsDetDTO[] authors) {
        this.authors = authors;
    }
}
