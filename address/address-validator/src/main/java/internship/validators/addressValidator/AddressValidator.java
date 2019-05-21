package internship.validators.addressValidator;

import internship.dao.userDAO.UserDAO;
import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressResponse;

public class AddressValidator implements IAddressValidator {
	private static final int MIN_SIZE_COUNTRY = 2;
	private static final int MAX_SIZE_COUNTRY = 50;
	private static final String COUNTRY_EXPRESSION_REG=String.format("^[0-9\\-\\s]{%d,%d}$", MIN_SIZE_COUNTRY, MAX_SIZE_COUNTRY);
	private static final String DOP_COUNTRY_EXPRESSION_REG=String.format("^[\\-\\s]{%d,%d}$", MIN_SIZE_COUNTRY, MAX_SIZE_COUNTRY);
	private static final String HOUSE_NUMBER_EXPRESSION_REG=String.format("^[0-9\\-\\s]{%d,%d}$", 1, 6);
	private static final String DOP_HOUSE_NUMBER_EXPRESSION_REG=String.format("^[\\-\\s]{%d,%d}$", 1, 6);


	private UserDAO userDao;

	private  BadAddressResponse badAddressResponse;

	void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public boolean isExitUserId(Long userId) {
		if (userDao.findUserById(userId) == null) {
			badAddressResponse.setUserId("Ошибка, несуществующий Id пользователя");
			return false;
		}
		return true;
	}

	public boolean isCountryValid(String country) {
		if (country == null) {
			badAddressResponse.setCrcs("Ошибка, поля country, city, street не должны быть пустые");
			return false;
		}
		String tempCountry = country.trim().replaceAll("\\s+", " ");
		if(!tempCountry.matches(COUNTRY_EXPRESSION_REG) || tempCountry.matches(DOP_COUNTRY_EXPRESSION_REG)){
			badAddressResponse.setCrcs("Ошибка, поля country, city, street не должны содержать цифры или просто пробелы");
			return false;
		}
		return true;
	}

	public boolean isHouseNumberValid(String numberHouse){
		if(numberHouse==null){
			badAddressResponse.setCrcs("Ошибка, поля HouseNumber и ApartmentNumber не могут быть пустыми");
			return false;
		}
		String temp = numberHouse.trim().replaceAll("\\s+", " ");
		if(!temp.matches(HOUSE_NUMBER_EXPRESSION_REG) || temp.matches(DOP_HOUSE_NUMBER_EXPRESSION_REG)){
			badAddressResponse.setCrcs("Ошибка, поля HouseNumber и ApartmentNumber не должны содержать буквы или просто пробелы");
			return false;
		}
		return true;
	}

	public boolean isValid(Address address) {
		badAddressResponse = new BadAddressResponse();
		return isExitUserId(address.getUserId()) &
				isCountryValid(address.getCountry())&
				isCountryValid(address.getCity())&
				isCountryValid(address.getRegion())&
				isCountryValid(address.getStreet())&
				isHouseNumberValid(address.getHouseNumber())&
				isHouseNumberValid(address.getApartmentNumber());
	}

	public BadAddressResponse getMessageError() {
		return badAddressResponse;
	}

	@Override
	public boolean isUserDAOUp() {
		return userDao != null;
	}
}
