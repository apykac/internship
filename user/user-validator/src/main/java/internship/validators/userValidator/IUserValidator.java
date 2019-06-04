package internship.validators.userValidator;

import internship.models.userModel.User;
import internship.validators.userValidator.models.ValidationResult;

import java.util.List;

public interface IUserValidator {

    /**
     * Проверяет адрес на корректность заполнения данных
     *
     * @param user Пользователь для проверки
     * @return Возвращает результат проверки
     */
    ValidationResult validate(User user);

    /**
     * Проверяет список адресов на корректность.
     *
     * @param users Пользователи для проверки
     * @return Возвращает результат проверки
     */
    ValidationResult validate(List<User> users);

    /**
     * Удаляет из списка пользователей невалидные
     * @param users Пользователи для проверки
     */
    void removeInvalidUsers(List<User> users);
}
