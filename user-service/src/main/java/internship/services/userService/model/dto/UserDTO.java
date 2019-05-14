package internship.services.userService.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
public class UserDTO {
    private String name;
    private String surname;
    private String patronymic;
    private String birthday;
    private String passportNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public UserDTO(String name, String surname, String patronymic, String birthday, String passportNumber) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.passportNumber = passportNumber;
    }
    public UserDTO(){}
}
