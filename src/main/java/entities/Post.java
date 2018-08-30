package entities;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private long id;

    private String content;

    private Date date;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Image image;

    @OneToOne
    private User taggedUser;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    private List<Vote> voteList;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "post")
    private List<Comment> commentList;

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

    public List<Vote> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public long getLikesCount() {
        long likes = 0;
        for (Vote vote :
                voteList) {
            if (vote.getType().equals("like")) {
                likes++;
            }
        }
        return likes;
    }

    public long getDislikesCount() {
        long dislikes = 0;
        for (Vote vote :
                voteList) {
            if (vote.getType().equals("dislike")) {
                dislikes++;
            }
        }
        return dislikes;
    }
}
