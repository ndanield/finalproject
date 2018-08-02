package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class City implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String province;

    private String postalCode;

    private String country;

    @OneToMany
    private List<WorkPlace> workPlaceList;

    @OneToMany
    private List<EstudyPlace> estudyPlaceList;

    public City() {
    }

    public City(String name, String province, String postalCode, String country, List<WorkPlace> workPlaceList, List<EstudyPlace> estudyPlaceList) {

        this.name = name;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.workPlaceList = workPlaceList;
        this.estudyPlaceList = estudyPlaceList;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<WorkPlace> getWorkPlaceList() {
        return workPlaceList;
    }

    public void setWorkPlaceList(List<WorkPlace> workPlaceList) {
        this.workPlaceList = workPlaceList;
    }

    public List<EstudyPlace> getEstudyPlaceList() {
        return estudyPlaceList;
    }

    public void setEstudyPlaceList(List<EstudyPlace> estudyPlaceList) {
        this.estudyPlaceList = estudyPlaceList;
    }
}
