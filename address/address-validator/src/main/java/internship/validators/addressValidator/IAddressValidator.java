package internship.validators.addressValidator;

import internship.models.addressModel.Address;
import internship.validators.addressValidator.response.BadAddressResponse;

import java.util.List;

public interface IAddressValidator {

    void isUserExists(Long userId);

    boolean isCountryValid(String country);

    boolean isValid(Address address);

    boolean isValid(List<Address> addresses);

    BadAddressResponse getMessageError();

    boolean isUserDAOUp();

}
