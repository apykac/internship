package internship.validators.addressValidator;

import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressResponse;

public interface IAddressValidator {

	boolean isExitUserId(Long userId);

	boolean isCountryValid(String country);

	boolean isHouseNumberValid(String numberHouse);

	boolean isValid(Address address);

	BadAddressResponse getMessageError();

	boolean isUserDAOUp();

}
