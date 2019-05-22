package internship.validators.userValidator;

import internship.models.userModel.User;
import internship.validators.userValidator.response.BadUserResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserValidator implements IUserValidator {
	private static final int MAX_SIZE_NAME = 50;
	private static final int MIN_SIZE_NAME = 2;
	private static final  String DATE_FORMAT = "dd-MM-yyyy";
	private static final  int PASSPORT_SIZE=10;
	private static final String USER_EXPRESSION_REG=String.format("^[а-яА-Я\\-\\s]{%d,%d}$", MIN_SIZE_NAME,MAX_SIZE_NAME);
	private static final String DOP_USER_EXPRESSION_REG="^[\\-\\s]{1,50}$";
	private static final String PASSPORT_NUMBER_EXPRESSION_REG=String.format("^[0-9]{%d}$",PASSPORT_SIZE);
	private static final String BIRTHDAY_EXPRESSION_REG="^([0-9]{1,2}\\-[0-9]{1,2}\\-[1][9][2-9][0-9])|([0-9]{1,2}\\-[0-9]{1,2}\\-[2][0][0-1][0-8])";
	private static final String INCOME_EXPRESSION_REG="^[0-9.]{1,10}$";

	private  BadUserResponse badUserResponse;

	public boolean isNameValid(String name) {
		if (name == null) {
			badUserResponse.setNsp("Поля name, surname, patronymic  не могут иметь пустое значение.");
			return false;
		}
		String temp=name.replaceAll("\\s+", " ").trim().replaceAll("\\-+","-");
		if(temp.length()!=name.length()){
			badUserResponse.setNsp("Поля name, surname, patronymic  содержат лишние символы пробела.");
			return false;
		}
		if(!temp.matches(USER_EXPRESSION_REG) || temp.matches(DOP_USER_EXPRESSION_REG)){
			badUserResponse.setNsp("Поля name, surname, patronymic  имеют неподходящие символы.");
			return false;
		}
		return true;
	}

	public boolean isDateBirthdayValid(String birthday) {
		if (birthday == null) {
			badUserResponse.setBirthday("Введено пустое значение для даты рождения");
			return false;
		}
		String temp=birthday.replace(".","-");

		if(!temp.matches(BIRTHDAY_EXPRESSION_REG)){
			badUserResponse.setBirthday("Введенна некорректная дата");
			return false;
		}
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(temp);
		} catch (ParseException e) {
			badUserResponse.setBirthday("Введен неправильный формат даты(формат должен быть dd-MM-yyyy или dd.MM.yyyy) или не существующая дата");
			return false;
		}
		return true;
	}

	public boolean isPassportNumberValid(Long passport) {
		if (passport == null) {
			badUserResponse.setPassportNumber("Пустое значение паспортных данных");
			return false;
		}
		if(!passport.toString().matches(PASSPORT_NUMBER_EXPRESSION_REG)){
			badUserResponse.setPassportNumber("Номер пасспорта должен содержать только 10 цифр");
			return false;
		}
		return true;
	}

	public boolean isIncomeValid(Double income){
		System.out.println(income);
		if(income==null){
			badUserResponse.setIncome("Пустое значение дохода");
			return false;
		}
		if(!(income.toString().matches(INCOME_EXPRESSION_REG))){
			badUserResponse.setIncome("Доход должен состоять из пололжительного числа, не превышающий 9.999.999.999");
			return false;
		}
		return true;
	}

	public boolean isValid(User user) {
		badUserResponse = new BadUserResponse();
		return (isNameValid(user.getName()) &
				isNameValid(user.getSurname()) &
				isNameValid(user.getPatronymic()) &
				isDateBirthdayValid(user.getBirthday()) &
				isPassportNumberValid(user.getPassportNumber())&
				isIncomeValid(user.getIncome()));
	}

	public BadUserResponse getErrorMessage() {
		return badUserResponse;
	}
}
