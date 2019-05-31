package internship.validators.addressValidator;

import internship.dao.userDAO.UserDAO;
import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.ValidationResult;

import java.util.List;
import java.util.Set;

public class AddressValidator implements IAddressValidator {

    private UserDAO userDao;
    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    //========================
    //  Interface methods
    //========================

    public ValidationResult validate(Address address) {
        ValidationResult vr = new ValidationResult();
        if (address==null){
            vr.addError("Валидатор получил null на вход");
            return vr;
        }
        validateUsers(vr, address.getUsers());
        validateCountry(vr, address.getCountry());
        validateRegion(vr, address.getRegion());
        validateCity(vr, address.getCity());
        validateStreet(vr, address.getStreet());
        return vr;
    }

    //TODO: Раньше валидация удаляла некорректные адреса из списка. Теперь не удаляет.
    public ValidationResult validate(List<Address> addresses){
        ValidationResult vr = new ValidationResult();
        if (addresses==null){
            vr.addError("Валидатор получил null на вход");
            return vr;
        }
        for(Address address:addresses){
            ValidationResult vr1 = validate(address);
            int i = 0;
            for (String error: vr1.getErrors()){
                vr.addError("Адрес["+ i + "]. " + error);
                i++;
            }
        }
        return vr;
    }

    @Override
    public boolean isUserDAOUp() {
        return userDao != null;
    }



    //========================
    //  Private methods
    //========================


    /**
     * Проверить существует ли пользователь в базе данных
     * @param passport
     */
    private void validateUserForExistance(ValidationResult vr, Long passport) {
        if (userDao.findUserByPassport(passport) == null) {
            vr.addError("Пользователь с номером паспорта " + passport + " не найден");
        }
    }

    private void validateUsers(ValidationResult vr, Set<Long> users){
        if (users==null || users.size()==0){
            vr.addError("Адрес должен иметь как минимум одного зарегестрированного в нём пользователя");
            return;
        }
        for (Long passportNumber : users) {
            validateUserForExistance(vr, passportNumber);
        }
    }

    private void validateCountry(ValidationResult vr, String country) {
        if (country == null) {
            vr.addError("Название страны не может быть пустым");
            return;
        }
        String trimedCountry = country.trim();
        if (trimedCountry.length() < 3) {
            vr.addError("Название страны не может быть короче 3 символов");
            return;
        }
        for (int i = 0; i < trimedCountry.length(); i++) {
            if (Character.isDigit(trimedCountry.charAt(i))) {
                vr.addError("Название страны не может содержать цифры");
                return;
            }
        }
    }

    private void validateRegion(ValidationResult vr, String region) {
        if (region == null) {
            vr.addError("Название региона не может быть пустым");
            return;
        }
        String trimedRegion = region.trim();
        if (trimedRegion.length() < 3) {
            vr.addError("Название региона не может быть короче 3 символов");
            return;
        }
        for (int i = 0; i < trimedRegion.length(); i++) {
            if (Character.isDigit(trimedRegion.charAt(i))) {
                vr.addError("Название региона не может содержать цифры");
                return;
            }
        }
    }

    private void validateCity(ValidationResult vr, String city) {
        if (city == null) {
            vr.addError("Название города не может быть пустым");
            return;
        }
        String trimedCity = city.trim();
        if (trimedCity.length() < 3) {
            vr.addError("Название города не может быть короче 3 символов");
            return;
        }
        for (int i = 0; i < trimedCity.length(); i++) {
            if (Character.isDigit(trimedCity.charAt(i))) {
                vr.addError("Название города не может содержать цифры");
                return;
            }
        }
    }

    private void validateStreet(ValidationResult vr, String street) {
        if (street == null) {
            vr.addError("Название улицы не может быть пустым");
            return;
        }
        String trimedStreet = street.trim();
        if (trimedStreet.length() < 3) {
            vr.addError("Название улицы не может быть короче 3 символов");
            return;
        }
        for (int i = 0; i < trimedStreet.length(); i++) {
            if (Character.isDigit(trimedStreet.charAt(i))) {
                vr.addError("Название улицы не может содержать цифры");
                return;
            }
        }
    }
}
