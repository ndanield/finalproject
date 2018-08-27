package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class User implements Serializable {

	@Id
    private String username;

    private String name;

    private String lastname;

    private Date birthdate;

    @Transient
    private int edad;

    private String password;

    private boolean isAdministrator;

    private String genre;

    private String nationality;

    private String city;

    @OneToOne
    private Image profileImage;

    @OneToOne
    private Image portraitImage;

//     De aqui para abajo son datos que se llenan al interactuar con el sistema
    @JoinTable(name = "FRIEND", joinColumns = {
        @JoinColumn(name = "USER1_USERNAME", referencedColumnName = "username", nullable = false)
    }, inverseJoinColumns = {
        @JoinColumn(name = "USER2_USERNAME", referencedColumnName = "username", nullable = false)
    })
    @ManyToMany
    private List<User> friendList;

    private String estudyPlace;

    private String workPlace;

//    @OneToMany(mappedBy = "user")
//    private List<Comment> commentList;
//
//    @OneToMany(mappedBy = "user")
//    private List<UserPost> userPostList;
//
//    @OneToMany(mappedBy = "user")
//    private List<CommentVote> commentVoteLi.st;

    public User() {
    }

    public User(String username, String name, String lastname, Date birthdate, String password, boolean isAdministrator, String city, Image profileImage) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.password = password;
        this.isAdministrator = isAdministrator;
        this.city = city;
        this.edad = calculateAge(birthdate);
        this.profileImage = profileImage;
        this.estudyPlace = null;
        this.workPlace = null;
    }

    private int calculateAge(Date birthdate) {
        LocalDate bd = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

        return Period.between(bd, currentDate).getYears();
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String sexo) {
        this.genre = sexo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEstudyPlace() {
        return estudyPlace;
    }

    public void setEstudyPlace(String estudyPlace) {
        this.estudyPlace = estudyPlace;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public Image getPortraitImage() {
        return portraitImage;
    }

    public void setPortraitImage(Image portraitImage) {
        this.portraitImage = portraitImage;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
