package internship.validators.addressValidator;

import internship.models.addressModel.Address;
import internship.validators.addressValidator.models.ValidationResult;

import java.util.List;

public interface IAddressValidator {

    /**
     * Проверяет адрес на корректность заполнения данных
     *
     * @param address Адрес для проверки
     * @return Возвращает результат проверки
     */
    ValidationResult validate(Address address);

    /**
     * Проверяет список адресов на корректность.
     *
     * @param addresses Адреса для проверки
     * @return Возвращает результат проверки
     */
    ValidationResult validate(List<Address> addresses);

    // TODO: Что это? По логике этого здесь не должно быть.
    boolean isUserDAOUp();

}
