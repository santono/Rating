package rating.dto;

import java.io.Serializable;


public class NtrDocRecDTO implements Serializable, Comparable<NtrDocRecDTO> {
    private static final long serialVersionUID = -5926456242001276041L;

    private long id;
    private String comment;
    private String filename;
    private String imageName;
    private String dateUploadStr;
    private java.util.Calendar dateUpload;
    private String title;
    private String mimetype;
    private int itIsImage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDateUploadStr() {
        return dateUploadStr;
    }

    public void setDateUploadStr(String dateUploadStr) {
        this.dateUploadStr = dateUploadStr;
    }

    public java.util.Calendar getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(java.util.Calendar dateUpload) {
        this.dateUpload = dateUpload;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItIsImage() {
        return itIsImage;
    }

    public void setItIsImage(int itIsImage) {
        this.itIsImage = itIsImage;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    @Override
    public int compareTo(NtrDocRecDTO o) {
        int retVal=0;
        if (getDateUpload().compareTo(getDateUpload())>0) {
            retVal=1;
        } else if (getDateUpload().compareTo(getDateUpload())<0) {
            retVal=-1;
        } else {
            retVal=0;
        }
        return retVal;
    }

}
