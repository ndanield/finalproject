package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class User implements Serializable {

    @Id
    private String username;

    private String name;

    private String lastname;

    private Date birthdate;

//    private String placeborn;

//    private String cityborn;

    private String password;

    private boolean isAdministrator;

    /*@OneToMany
    private List<User> friendList;*/

//    @OneToMany
//    private List<EstudyPlace> estudyPlace;
//
//    @OneToMany
//    private List<WorkPLace> workPlace;
//
//    private List<String> interests;
//
//    @OneToMany
//    private List<Notification> notificationList;
//
//    @OneToMany(mappedBy = "user")
//    private List<Comment> commentList;
//
//    @OneToMany(mappedBy = "user")
//    private List<UserPost> userPostList;
//
//    @OneToMany(mappedBy = "user")
//    private List<CommentVote> commentVoteList;

    public User() {
    }

    public User(String username, String name, String lastname, Date birthdate, String password, boolean isAdministrator) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.password = password;
        this.isAdministrator = isAdministrator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
}
