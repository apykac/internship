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

    /**
     * Удаляет из списка адресов невалидные
     *
     * @param addresses Адреса для проверки
     */
    void removeInvalidAddresses(List<Address> addresses);

    /**
     * Проверяет имеется ли у валидатора ссылка на сервис user-dao
     *
     * @return Возвращает true, если всё в порядке
     */
    boolean isUserDAOUp();

}
