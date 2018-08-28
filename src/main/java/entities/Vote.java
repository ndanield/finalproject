package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Vote {
    @Id
    @GeneratedValue
    private Long id;

    private String type;

    @ManyToOne
    private User user;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private Post post;

    public Vote() {
    }

    public Vote(String type, User user, Comment comment) {
        this.type = type;
        this.user = user;
        this.comment = comment;
    }

    public Vote(String type, User user, Post post) {
        this.type = type;
        this.user = user;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
