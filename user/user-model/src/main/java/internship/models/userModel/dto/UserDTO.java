package internship.models.userModel.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "User")
public class UserDTO {
    private String name;
    private String surname;
    private String patronymic;
    private String birthday;
    private Long passportNumber;
    private Long income;

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

    public Long getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(Long passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public UserDTO(String name, String surname, String patronymic, String birthday, Long passportNumber, Long income) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.passportNumber = passportNumber;
        this.income = income;
    }
    public UserDTO(){}
}
