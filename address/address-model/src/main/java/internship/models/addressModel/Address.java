package internship.models.addressModel;

import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlRootElement(name = "Address")
@XmlAccessorType(XmlAccessType.FIELD)
public class Address {
    private long id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;

    @XmlElementWrapper(name = "Users")
    @XmlElement(name = "user")
    private Set<Long> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Long> getUsers() {
        return users;
    }

    public void setUserId(Set<Long> users) {
        this.users = users;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Address() {
    }

    public Address(long id, Set<Long> users, String country, String region, String city, String street, String houseNumber, String apartmentNumber) {
        this.id = id;
        this.users = users;
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public Address(Set<Long> users, String country, String region, String city, String street, String houseNumber, String apartmentNumber) {
        this.users = users;
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }
}
