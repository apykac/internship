package internship.dao.addressDAO;

import internship.models.addressModel.Address;
import internship.models.addressModel.dto.AddressDTO;

public interface AddressDAO {
	Address findAddressById(Long id);

	Address updateAddress(Long id, AddressDTO addressDTO);

	Address createAddress(AddressDTO addressDTO);

	void removeAddress(Long id);
}
