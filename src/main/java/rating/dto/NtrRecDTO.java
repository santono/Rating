package rating.dto;




public class NtrRecDTO {
    private int id;
    private String name;
    private String approved;
    private String dataapproved;
    private String fioapproved;
    private boolean hasattachement;
    private String authors;
    private String namepre;
    private String parametry;
    private String pokaz;
    private int amntOfImages;
    private int amntOfDocs;
    private int lineno;
    private int yPubl;


    public String getNamepre() {
        return namepre;
    }

    public void setNamepre(String namepre) {
        this.namepre = namepre;
    }

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

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getDataapproved() {
        return dataapproved;
    }

    public void setDataapproved(String dataapproved) {
        this.dataapproved = dataapproved;
    }

    public boolean isHasattachement() {
        return hasattachement;
    }

    public void setHasattachement(boolean hasattachement) {
        this.hasattachement = hasattachement;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getParametry() {
        return parametry;
    }

    public void setParametry(String parametry) {
        this.parametry = parametry;
    }

    public String getPokaz() {
        return pokaz;
    }

    public void setPokaz(String pokaz) {
        this.pokaz = pokaz;
    }

    public int getAmntOfImages() {
        return amntOfImages;
    }

    public void setAmntOfImages(int amntOfImages) {
        this.amntOfImages = amntOfImages;
    }

    public int getAmntOfDocs() {
        return amntOfDocs;
    }

    public void setAmntOfDocs(int amntOfDocs) {
        this.amntOfDocs = amntOfDocs;
    }

    public String getFioapproved() {
        return fioapproved;
    }

    public void setFioapproved(String fioapproved) {
        this.fioapproved = fioapproved;
    }

    public int getLineno() {
        return lineno;
    }

    public void setLineno(int lineno) {
        this.lineno = lineno;
    }

    public int getyPubl() {
        return yPubl;
    }

    public void setyPubl(int yPubl) {
        this.yPubl = yPubl;
    }
}
