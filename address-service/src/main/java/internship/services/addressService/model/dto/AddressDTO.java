package internship.services.addressService.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Address")
public class AddressDTO {
    private long userId;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String apartmentNumber;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public AddressDTO(long userId, String country, String city, String street, String houseNumber, String apartmentNumber) {
        this.userId = userId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }

    public AddressDTO(){}
}
