package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class Notification implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private NotificationType type;

    private String description;

    private boolean seen;

    @ManyToOne
    private User user;

    private Date date;

    public Notification() {
    }

    public Notification(long id, NotificationType type, String description, boolean seen, User user, Date date) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.seen = seen;
        this.user = user;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
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
}
