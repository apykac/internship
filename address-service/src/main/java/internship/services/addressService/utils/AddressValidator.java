package internship.services.addressService.utils;

import internship.services.addressService.model.dto.AddressDTO;
import internship.services.addressService.response.BadAddressResponse;
import internship.services.userService.dao.UserDAO;
import internship.services.userService.dao.UserHashMapDAO;

public class AddressValidator {
    private AddressDTO address;
    private UserDAO userDao = UserHashMapDAO.getInstance();
    private BadAddressResponse badAddressResponse = new BadAddressResponse();

    public AddressValidator(AddressDTO address) {
        this.address = address;
    }

    private boolean isExitUserId(Long userId) {
        if (userDao.findUserById(userId) == null) {
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
