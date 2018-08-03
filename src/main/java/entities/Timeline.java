package entities;

import entities.Post;
import entities.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Timeline {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private User user;

    private Date date;

    @OneToMany
    private List<Post> postList;

    public Timeline() {
    }

    public Timeline(User user, Date date) {
        this.user = user;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}
