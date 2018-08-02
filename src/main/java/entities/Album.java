package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class Album {
    @Id
    @GeneratedValue
    private long id;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String description;

    @OneToMany
    private List<Image> image;

    public Album() {
    }

    public Album(Date date, User user, String description) {
        this.date = date;
        this.user = user;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }
}
