package internship.validators.addressValidator;

import internship.dao.userDAO.UserDAO;
import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.ValidationError;
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
            vr.addError(new ValidationError("Address", "Значение не должно быть null"));
            return vr;
        }
        validateUsers(vr, address.getUsers());
        validateCountry(vr, address.getCountry());
        validateRegion(vr, address.getRegion());
        validateCity(vr, address.getCity());
        validateStreet(vr, address.getStreet());
        validateHouseNumber(vr, address.getHouseNumber());
        validateApartmentNumber(vr, address.getApartmentNumber());
        return vr;
    }

    //TODO: Раньше валидация удаляла некорректные адреса из списка. Теперь не удаляет.
    public ValidationResult validate(List<Address> addresses){
        ValidationResult vr = new ValidationResult();
        if (addresses==null){
            vr.addError(new ValidationError("Address", "Значение не должно быть null"));
            return vr;
        }
        for(Address address:addresses){
            ValidationResult vr1 = validate(address);
            for (int i = 0; i<vr1.getErrors().size(); i++){
                ValidationError error = vr1.getErrors().get(i);
                vr.addError(new ValidationError("address["+i+"]:"+error.getCause(), error.getMessage()));
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
            vr.addError(new ValidationError("User","Пользователь с номером паспорта " + passport + " не найден"));
        }
    }

    private void validateUsers(ValidationResult vr, Set<Long> users){
        if (users==null || users.size()==0){
            vr.addError(new ValidationError("Users", "Адрес должен иметь как минимум одного зарегестрированного в нём пользователя"));
            return;
        }
        int userNum = 0;
        for (Long passportNumber : users) {
            validateUserForExistance(vr, passportNumber);
        }
    }

    private void validateCountry(ValidationResult vr, String country) {
        validateCommonLocation(vr, "Country", country);
    }

    private void validateRegion(ValidationResult vr, String region) {
        validateCommonLocation(vr, "Region", region);
    }

    private void validateCity(ValidationResult vr, String city) {
        validateCommonLocation(vr, "City", city);
    }

    private void validateStreet(ValidationResult vr, String street) {
        validateCommonLocation(vr, "Street", street);
    }

    private void validateHouseNumber(ValidationResult vr, String houseNumber){
        validateCommonNumber(vr, "houseNumber", houseNumber);
    }

    private void validateApartmentNumber(ValidationResult vr, String apartmentNumber){
        validateCommonNumber(vr, "apartmentNumber", apartmentNumber);
    }

    private void validateCommonLocation(ValidationResult vr, String cause, String value){
        if (value == null) {
            vr.addError(new ValidationError(cause,"Значение не может быть пустым"));
            return;
        }
        String trimedValue = value.trim();
        if (trimedValue.length() < 3) {
            vr.addError(new ValidationError(cause,"Значение не может быть короче 3 символов"));
            return;
        }
        for (int i = 0; i < trimedValue.length(); i++) {
            if (Character.isDigit(trimedValue.charAt(i))) {
                vr.addError(new ValidationError(cause, "Значение не может содержать цифры"));
                return;
            }
        }
    }

    private void validateCommonNumber(ValidationResult vr, String cause, String value){
        if (value==null || value.length()==0){
            vr.addError(new ValidationError(cause, "Значение не может быть пустым"));
            return;
        }
        String trimedHouseNumber = value.trim();
        try {
            int num = Integer.parseInt(trimedHouseNumber);
            if (num<=0){
                vr.addError(new ValidationError(cause, "Значение должно быть положительным"));
            }
        }
        catch (Exception e){
            vr.addError(new ValidationError(cause, "Значение должно быть числом"));
        }
    }
}
