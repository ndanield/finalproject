package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "comment")
    private List<Vote> voteList;

    private Date date;

    public Comment() {
    }

    public Comment(String content, User user, Post post, Date date) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
