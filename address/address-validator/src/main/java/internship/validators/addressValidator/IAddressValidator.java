package internship.validators.addressValidator;

import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressListResponce;
import internship.validators.addressValidator.response.BadAddressResponse;

import java.util.List;

public interface IAddressValidator {

	boolean isValidAddressList(List<Address> addresses);

	boolean isValid(Address address);

	BadAddressResponse getMessageError();

	BadAddressListResponce getBadAddressListResponse();

	boolean isUserDAOUp();


}
