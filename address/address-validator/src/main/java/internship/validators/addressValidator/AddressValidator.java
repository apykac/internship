package internship.validators.addressValidator;

import internship.dao.userDAO.UserDAO;
import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressResponse;

//FIXME После непройденной валидации оишбки остаются даже при вводе верных данных
public class AddressValidator implements IAddressValidator {

	private UserDAO userDao;

	private final BadAddressResponse badAddressResponse = new BadAddressResponse();

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
			badAddressResponse.setCountry("Ошибка, поля country, city, street не должны быть пустые");
			return false;
		}
		String tempCountry = country.trim();
		if (tempCountry.length() < 3) {
			badAddressResponse.setCountry("Ошибка, поля country, city, street должны быть больше 3 символов");
			return false;
		}
		for (int i = 0; i < tempCountry.length(); i++) {
			if (Character.isDigit(tempCountry.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean isValid(Address address) {
		return isExitUserId(address.getUserId()) &
				isCountryValid(address.getCountry());
	}

	public BadAddressResponse getMessageError() {
		return badAddressResponse;
	}

	@Override
	public boolean isUserDAOUp() {
		return userDao != null;
	}
}
