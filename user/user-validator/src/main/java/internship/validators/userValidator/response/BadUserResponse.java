package internship.validators.userValidator.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="baduserresponce")
public class BadUserResponse {
    private String nsp;
    private String birthday;
    private String passportNumber;

    public String getNsp() {
        return nsp;
    }

    public void setNsp(String nsp) {
        this.nsp = nsp;
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
}
