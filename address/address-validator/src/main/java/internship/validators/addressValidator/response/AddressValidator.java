package internship.validators.addressValidator.response;

import internship.dao.userDAO.UserDAO;
import internship.dao.userDAO.UserDatabaseDAO;
import internship.dao.userDAO.UserHashMapDAO;
import internship.models.addressModel.dto.AddressDTO;

public class AddressValidator {
	private AddressDTO address;
	private UserDAO userDao = UserHashMapDAO.getInstance();
    private UserDAO userDbDao = new UserDatabaseDAO();
	private BadAddressResponse badAddressResponse = new BadAddressResponse();

	public AddressValidator(AddressDTO address) {
		this.address = address;
	}

	private boolean isExitUserId(Long userId) {
        if (userDbDao.findUserById(userId) == null) {
			badAddressResponse.setUserId("Ошибка, несуществующий Id пользователя");
			return false;
		}
		return true;
	}

	private boolean isCountryValid(String country) {
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

	public boolean isValid() {
		return isExitUserId(address.getUserId()) &
				isCountryValid(address.getCountry());
	}

	public BadAddressResponse getMessageError() {
		return badAddressResponse;
	}
}