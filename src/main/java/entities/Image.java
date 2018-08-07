package entities;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private long id;

    private String path;

    public Image() {
    }

    public Image(String path) {
        this.path = path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
