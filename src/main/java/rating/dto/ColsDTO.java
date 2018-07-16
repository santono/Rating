package rating.dto;


import java.io.Serializable;

public class ColsDTO implements Serializable {
    private int cols[];

    public int[] getCols() {
        return cols;
    }

    public void setCols(int[] cols) {
        this.cols = cols;
    }
}
