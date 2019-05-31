package internship.validators.addressValidator;

import internship.dao.addressDAO.AddressDAO;
import internship.dao.userDAO.UserDAO;
import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressResponse;

import java.util.List;

public class AddressValidator implements IAddressValidator {

    private UserDAO userDao;
    private AddressDAO addressDAO;

    private BadAddressResponse badAddressResponse;

    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    void setAddressDao(AddressDAO addressDao) {
        this.addressDAO = addressDao;
    }

    public void isUserExists(Long userId) {
        if (userDao.findUserById(userId) == null) {
            badAddressResponse.getUserId().add("Пользователь с ID " + userId + " не найден");
        }
    }

    public boolean isAddressExists(Long addressId) {
        if (addressDAO.findAddressById(addressId) == null) {
            badAddressResponse.setAddress("Адрес с таким ID не существует");
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

    public boolean isValid(List<Address> addresses){
        for(int i=0;i<addresses.size();i++){
            if(!isValidForList(addresses.get(i))){
                addresses.remove(i);
            }
        }
        return addresses.size()!=0;
    }

    private boolean isValidForList(Address address){
        return isCountryValid(address.getRegion());
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
