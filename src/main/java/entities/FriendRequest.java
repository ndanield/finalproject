package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FriendRequest implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne()
    private User requestUser;

    @OneToOne()
    private User targetUser;

    private boolean isAccepted;

    public FriendRequest() {
    }

    public FriendRequest(long id, User userFrom, User userRequested, boolean isAccepted) {
        this.id = id;
        this.requestUser = userFrom;
        this.targetUser = userRequested;
        this.isAccepted = isAccepted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(User requestUser) {
        this.requestUser = requestUser;
    }

    public User getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(User userRequested) {
        this.targetUser = userRequested;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
