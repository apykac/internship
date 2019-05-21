package internship.models.userModel;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "User")
public class User {
	private long id;
	private String name;
	private String surname;
	private String patronymic;
	private String birthday;
	private Long passportNumber;
	private Double income;



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

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public User() {
	}

	public User(long id, String name, String surname, String patronymic, String birthday, Long passportNumber, Double income) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
		this.birthday = birthday;
		this.passportNumber = passportNumber;
		this.income = income;
	}

	public User(String name, String surname, String patronymic, String birthday, Long passportNumber, Double income) {
		this.name = name;
		this.surname = surname;
		this.patronymic = patronymic;
		this.birthday = birthday;
		this.passportNumber = passportNumber;
		this.income = income;
	}
}
