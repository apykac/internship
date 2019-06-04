package internship.validators.addressValidator;

import internship.dao.addressDAO.AddressDAO;
import internship.dao.userDAO.UserDAO;
import internship.models.addressModel.Address;
import internship.validators.addressValidator.models.ValidationError;
import internship.validators.addressValidator.models.ValidationResult;

import java.util.List;
import java.util.Set;

public class AddressValidator implements IAddressValidator {

    //=================================
    // Smth for services connection
    //=================================

    private UserDAO userDao;

    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    private AddressDAO addressDao;

    void setAddressDao(AddressDAO addressDao) {
        this.addressDao = addressDao;
    }

    //========================
    //  Interface methods
    //========================

    public ValidationResult validate(Address address) {
        ValidationResult vr = new ValidationResult();
        if (address == null) {
            vr.addError(new ValidationError("Address", "Значение не должно быть null"));
            return vr;
        }
        validateCountry(vr, address.getCountry());
        validateRegion(vr, address.getRegion());
        validateCity(vr, address.getCity());
        validateStreet(vr, address.getStreet());
        validateHouseNumber(vr, address.getHouseNumber());
        validateApartmentNumber(vr, address.getApartmentNumber());
        if (vr.isValid()){
            validateUsers(vr, address.getUsers());
        }
        return vr;
    }


    public ValidationResult validate(List<Address> addresses) {
        ValidationResult vr = new ValidationResult();
        if (addresses == null) {
            vr.addError(new ValidationError("Address", "Значение не должно быть null"));
            return vr;
        }

        for (int i = 0; i< addresses.size(); i++) {
            ValidationResult vr1 = validate(addresses.get(i));
            for (ValidationError error: vr1.getErrors()) {
                vr.addError(new ValidationError("address[" + i + "]:" + error.getCause(), error.getMessage()));
            }
        }
        return vr;
    }

    @Override
    public boolean isUserDAOUp() {
        return userDao != null;
    }

    public void removeInvalidAddresses(List<Address> addresses){
        addresses.removeIf(address -> !validate(address).isValid());
    }


    //========================
    //  Private methods
    //========================


    /**
     * Проверить существует ли пользователь в базе данных
     *
     * @param vr       Куда добавлять сообщения об ошибках
     * @param passport Номер паспорта для проверки
     */
    private void validateUserForExistence(ValidationResult vr, Long passport) {
        if (userDao.findUserByPassport(passport) == null) {
            vr.addError(new ValidationError("User", "Пользователь с номером паспорта " + passport + " не найден"));
        }
    }

    /**
     * Провалидировать содержимое тега <Users></Users>
     *
     * @param vr    Куда добавлять сообщения об ошибках
     * @param users Список пользователей для проверки
     */
    private void validateUsers(ValidationResult vr, Set<Long> users) {
        if (users == null || users.size() == 0) {
            vr.addError(new ValidationError("Users", "Адрес должен иметь как минимум одного зарегестрированного в нём пользователя"));
            return;
        }
        int userNum = 0;
        for (Long passportNumber : users) {
            validateUserForExistence(vr, passportNumber);
        }
    }

    /**
     * Провалидировать содержимое тега <country></country>
     *
     * @param vr      Куда добавлять сообщения об ошибках
     * @param country Значение для проверки
     */
    private void validateCountry(ValidationResult vr, String country) {
        validateCommonLocation(vr, "Country", country);
    }

    /**
     * Провалидировать содержимое тега <region></region>
     *
     * @param vr     Куда добавлять сообщения об ошибках
     * @param region Значение для проверки
     */
    private void validateRegion(ValidationResult vr, String region) {
        validateCommonLocation(vr, "Region", region);
    }

    /**
     * Провалидировать содержимое тега <city></city>
     *
     * @param vr   Куда добавлять сообщения об ошибках
     * @param city Значение для проверки
     */
    private void validateCity(ValidationResult vr, String city) {
        validateCommonLocation(vr, "City", city);
    }

    /**
     * Провалидировать содержимое тега <street></street>
     *
     * @param vr     Куда добавлять сообщения об ошибках
     * @param street Значение для проверки
     */
    private void validateStreet(ValidationResult vr, String street) {
        validateCommonLocation(vr, "Street", street);
    }

    /**
     * Провалидировать содержимое тега <houseNumber></houseNumber>
     *
     * @param vr          Куда добавлять сообщения об ошибках
     * @param houseNumber Значение для проверки
     */
    private void validateHouseNumber(ValidationResult vr, String houseNumber) {
        validateCommonNumber(vr, "houseNumber", houseNumber);
    }

    /**
     * Провалидировать содержимое тега <apartmentNumber></apartmentNumber>
     *
     * @param vr              Куда добавлять сообщения об ошибках
     * @param apartmentNumber Значение для проверки
     */
    private void validateApartmentNumber(ValidationResult vr, String apartmentNumber) {
        validateCommonNumber(vr, "apartmentNumber", apartmentNumber);
    }

    /**
     * Провалидировать тег Country\Region\City\Street. Потому что к ним предъявляются одинаковые требования.
     *
     * @param vr    Куда добавлять сообщения об ошибках
     * @param cause Название валидируемого тега
     * @param value Значение для проверки
     */
    private void validateCommonLocation(ValidationResult vr, String cause, String value) {
        if (value == null) {
            vr.addError(new ValidationError(cause, "Значение не может быть пустым"));
            return;
        }
        String trimedValue = value.trim();
        if (trimedValue.length() < 3) {
            vr.addError(new ValidationError(cause, "Значение не может быть короче 3 символов"));
            return;
        }
        for (int i = 0; i < trimedValue.length(); i++) {
            if (Character.isDigit(trimedValue.charAt(i))) {
                vr.addError(new ValidationError(cause, "Значение не может содержать цифры"));
                return;
            }
        }
    }

    /**
     * Провалидировать тег houseNumber\apartmentNumber. Потому что к ним предъявляются одинаковые требования.
     *
     * @param vr    Куда добавлять сообщения об ошибках
     * @param cause Название валидируемого тега
     * @param value Значение для проверки
     */
    private void validateCommonNumber(ValidationResult vr, String cause, String value) {
        if (value == null || value.length() == 0) {
            vr.addError(new ValidationError(cause, "Значение не может быть пустым"));
            return;
        }
        String trimmedNumber = value.trim();
        try {
            int num = Integer.parseInt(trimmedNumber);
            if (num <= 0) {
                vr.addError(new ValidationError(cause, "Значение должно быть положительным"));
            }
        } catch (Exception e) {
            vr.addError(new ValidationError(cause, "Значение должно быть числом"));
        }
    }
}
