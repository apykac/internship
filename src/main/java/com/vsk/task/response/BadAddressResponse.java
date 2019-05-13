package com.vsk.task.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BadAddressResponse")
public class BadAddressResponse {
    private String userId;
    private String country;
    private String city;
    private String street;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
}
