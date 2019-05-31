package internship.validators.addressValidator;

import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.ValidationResult;

import java.util.List;

public interface IAddressValidator {

    /**
     * Проверяет адрес на корректность заполненных данных.
     */
    ValidationResult validate(Address address);

    /**
     * Проверяет список адресов на корректность.
     */
    ValidationResult validate(List<Address> addresses);

    boolean isUserDAOUp();

}
