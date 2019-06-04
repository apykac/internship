package internship.dao.addressDAO;

import internship.models.addressModel.Address;
import internship.models.addressModel.Addresses;

public interface AddressDAO {

    Address findAddressById(Long id);

    Addresses findAddressesByUserPassport(Long passport);

    Address updateAddress(Long id, Address address);

    Address createAddress(Address address);

    void removeAddress(Long id);

    boolean isConnectorUp();
}
