package internship.dao.addressDAO;

import internship.models.addressModel.Address;

public interface AddressDAO {

	Address findAddressById(Long id);

	Address updateAddress(Long id, Address address);

	Address createAddress(Address address);

	void removeAddress(Long id);

	boolean isConnectorUp();
}
