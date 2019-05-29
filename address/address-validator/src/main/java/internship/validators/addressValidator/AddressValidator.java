package internship.validators.addressValidator;

import internship.dao.userDAO.UserDAO;
import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressResponse;

public class AddressValidator implements IAddressValidator {

    private UserDAO userDao;

    private BadAddressResponse badAddressResponse;

    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void isUserExists(Long userId) {
        if (userDao.findUserById(userId) == null) {
            badAddressResponse.getUserId().add("Пользователь с ID " + userId + " не найден");
        }
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
        badAddressResponse = new BadAddressResponse();

        for (Long userId : address.getUserId()) {
            isUserExists(userId);
        }

        return badAddressResponse.getUserId().size() == 0 &
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
