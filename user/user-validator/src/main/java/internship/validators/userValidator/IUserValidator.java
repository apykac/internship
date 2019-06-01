package internship.validators.userValidator;

import internship.models.userModel.User;
import internship.validators.userValidator.response.BadUserResponse;

import java.util.List;

public interface IUserValidator {

    boolean isNameValid(String name);

    boolean isDateBirthdayValid(String birthday);

    boolean isPassportNumberValid(Long passport);

    boolean isValid(User user);

    boolean isListValid(List<User> users);

    boolean isUserExistsPost(Long passport);

    boolean isUserExistsPut(Long passport, Long passportForUpdate);

    BadUserResponse getErrorMessage();
}
