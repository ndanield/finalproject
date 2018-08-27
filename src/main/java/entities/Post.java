package entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private long id;

    private String content;

    private Date date;

    @ManyToOne
    private User user;

    @OneToOne
    private Image image;

    @OneToOne
    private User taggedUser;

    public Post() {
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Post(String content, Date date, User user, Image image, User taggedUser) {
        this.content = content;
        this.date = date;
        this.user = user;
        this.image = image;
        this.taggedUser = taggedUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public User getTaggedUser() {
        return taggedUser;
    }

    public void setTaggedUser(User taggedUser) {
        this.taggedUser = taggedUser;
    }
}
