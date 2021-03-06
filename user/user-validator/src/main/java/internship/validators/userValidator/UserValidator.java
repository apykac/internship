package internship.validators.userValidator;

import internship.dao.userDAO.UserDAO;
import internship.models.userModel.User;
import internship.validators.userValidator.models.ValidationError;
import internship.validators.userValidator.models.ValidationResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserValidator implements IUserValidator {

    //=================================
    // Smth for services connection
    //=================================

    private UserDAO userDao;

    void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    //========================
    //  Interface methods
    //========================

    public ValidationResult validate(User user) {
        ValidationResult vr = new ValidationResult();
        if (user == null) {
            vr.addError(new ValidationError("User", "Значение не должно быть null"));
            return vr;
        }

        validateName(vr, user.getName());
        validateSurname(vr, user.getSurname());
        validatePatronymic(vr, user.getPatronymic());
        validateBirthday(vr, user.getBirthday());
        validatePassportNumber(vr, user.getPassportNumber());
        validateIncome(vr, user.getIncome());
        return vr;
    }


    public ValidationResult validate(List<User> users) {
        ValidationResult vr = new ValidationResult();
        if (users == null) {
            vr.addError(new ValidationError("User", "Значение не должно быть null"));
            return vr;
        }
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            ValidationResult vr1 = validate(user);
            for (ValidationError error : vr1.getErrors()) {
                vr.addError(new ValidationError("user[" + i + "]:" + error.getCause(), error.getMessage()));
            }
        }
        return vr;
    }

    public void removeInvalidUsers(List<User> users) {
        users.removeIf(user -> !validate(user).isValid());
    }


    //========================
    //  Private methods
    //========================

    /**
     * Провалидировать содержимое тега <name></name>
     *
     * @param vr   Куда добавлять сообщения об ошибках
     * @param name Значение для проверки
     */
    private void validateName(ValidationResult vr, String name) {
        validateCommonName(vr, "Name", name);
    }

    /**
     * Провалидировать содержимое тега <surname></surname>
     *
     * @param vr      Куда добавлять сообщения об ошибках
     * @param surname Значение для проверки
     */
    private void validateSurname(ValidationResult vr, String surname) {
        validateCommonName(vr, "Surname", surname);
    }

    /**
     * Провалидировать содержимое тега <patronymic></patronymic>
     *
     * @param vr         Куда добавлять сообщения об ошибках
     * @param patronymic Значение для проверки
     */
    private void validatePatronymic(ValidationResult vr, String patronymic) {
        validateCommonName(vr, "Patronymic", patronymic);
    }

    /**
     * Провалидировать содержимое тега <birthday></birthday>
     *
     * @param vr       Куда добавлять сообщения об ошибках
     * @param birthday Значение для проверки
     */
    private void validateBirthday(ValidationResult vr, String birthday) {
        if (birthday == null) {
            vr.addError(new ValidationError("Birthday", "Значение не может быть пустым"));
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(birthday, formatter);
            if (date.getYear() < 1900 || date.getYear() > 2019) {
                vr.addError(new ValidationError("Birthday", "Введена некорректная дата (год не может быть меньше 1900 или больше 2019)"));
            }
        } catch (Exception e) {
            vr.addError(new ValidationError("Birthday", "Введен неправильный формат даты (формат должен быть dd-MM-yyyy) или несуществующая дата"));
        }
    }

    /**
     * Провалидировать содержимое тега <passportNumber></passportNumber>
     *
     * @param vr             Куда добавлять сообщения об ошибках
     * @param passportNumber Значение для проверки
     */
    private void validatePassportNumber(ValidationResult vr, Long passportNumber) {
        validateCommonNumber(vr, "Passport Number", passportNumber);
    }

    /**
     * Провалидировать содержимое тега <income></income>
     *
     * @param vr     Куда добавлять сообщения об ошибках
     * @param income Значение для проверки
     */
    private void validateIncome(ValidationResult vr, Long income) {
        validateCommonNumber(vr, "Income", income);
    }

    /**
     * Провалидировать тег Name\Surname\Patronymic. Потому что к ним предъявляются одинаковые требования.
     *
     * @param vr    Куда добавлять сообщения об ошибках
     * @param cause Название валидируемого тега
     * @param value Значение для проверки
     */
    private void validateCommonName(ValidationResult vr, String cause, String value) {
        if (value == null) {
            vr.addError(new ValidationError(cause, "Значение не может быть пустым"));
            return;
        }
        String trimmedValue = value.trim();
        if (trimmedValue.length() < 3 || trimmedValue.length() > 50) {
            vr.addError(new ValidationError(cause, "Значение не может быть короче 3 символов или длиннее 50 символов"));
            return;
        }
        for (int i = 0; i < trimmedValue.length(); i++) {
            if (Character.isDigit(trimmedValue.charAt(i))) {
                vr.addError(new ValidationError(cause, "Значение не может содержать цифры"));
                return;
            }
        }
    }

    /**
     * Провалидировать тег passportNumber\income. Потому что к ним предъявляются одинаковые требования.
     *
     * @param vr    Куда добавлять сообщения об ошибках
     * @param cause Название валидируемого тега
     * @param value Значение для проверки
     */
    private void validateCommonNumber(ValidationResult vr, String cause, Long value) {
        if (value == null) {
            vr.addError(new ValidationError(cause, "Значение не может быть пустым, либо содержать символы, отличные от цифр"));
            return;
        }
        if (value < 0) {
            vr.addError(new ValidationError(cause, "Значение должно быть положительным"));
        }
    }


}
