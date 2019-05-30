package internship.validators.addressValidator;

import internship.dao.userDAO.UserDAO;
import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressListResponce;
import internship.validators.addressValidator.response.BadAddressResponse;

import java.util.List;

//FIXME После непройденной валидации оишбки остаются даже при вводе верных данных
public class AddressValidator implements IAddressValidator {

    private UserDAO userDao;

    private BadAddressResponse badAddressResponse;
    private BadAddressListResponce badAddressListResponce;


    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
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
            badAddressResponse.setCountry("Ошибка, поля country, city, street должны быть больше 3 символов,"+"вы ввели: "+country);
            return false;
        }
        for (int i = 0; i < tempCountry.length(); i++) {
            if (Character.isDigit(tempCountry.charAt(i))) {
                badAddressResponse.setCountry("Ошибка, поля country, city, street не должны содержать цифры,"+"вы ввели: "+country);
                return false;
            }
        }
        return true;
    }

    public boolean isValid(Address address) {
        badAddressResponse = new BadAddressResponse();
        return isExitUserId(address.getUserId()) &
                isCountryValid(address.getCountry());
    }

    private boolean isValidWithoutUser(Address address) {
        badAddressResponse = new BadAddressResponse();
        return isCountryValid(address.getCountry())&
                isCountryValid(address.getRegion());
    }

    @Override
    public boolean isValidAddressList(List<Address> addresses) {
        boolean isAllListValid = true;
        badAddressListResponce = new BadAddressListResponce();
        for (Address address : addresses) {
            if (!isValidWithoutUser(address)) {
                badAddressListResponce.addBadAddressResponse(badAddressResponse);
                isAllListValid = false;
            }
        }
        return isAllListValid;
    }

    public BadAddressListResponce getBadAddressListResponse() {
        return badAddressListResponce;
    }

    public BadAddressResponse getMessageError() {
        return badAddressResponse;
    }

    public boolean isUserDAOUp() {
        return userDao != null;
    }
}
