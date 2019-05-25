package internship.validators.addressValidator;

import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressResponse;

public interface IAddressValidator {

    void isUserExists(Long userId);

	boolean isCountryValid(String country);

	boolean isValid(Address address);

	BadAddressResponse getMessageError();

	boolean isUserDAOUp();

}
