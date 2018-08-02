package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class WorkPlace implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String description;

    private Date dateFounded;

    private String telephone;

    @ManyToOne
    private City city;

    @ManyToOne
    private User user;

    private String mail;



    public WorkPlace() {
    }

    public WorkPlace(String name, String description, Date dateFounded, String telephone, City city, User user, String mail) {
        this.name = name;
        this.description = description;
        this.dateFounded = dateFounded;
        this.telephone = telephone;
        this.city = city;
        this.user = user;
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateFounded() {
        return dateFounded;
    }

    public void setDateFounded(Date dateFounded) {
        this.dateFounded = dateFounded;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
