package internship.validators.userValidator;

import internship.models.userModel.User;
import internship.validators.userValidator.response.BadUserResponse;

public interface IUserValidator {

    boolean isNameValid(String name);

    boolean isDateBirthdayValid(String birthday);

    boolean isPassportNumberValid(Long passport);

    boolean isValid(User user);

    BadUserResponse getErrorMessage();
}
