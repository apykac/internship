package internship.services.addressService.dao;

import internship.services.addressService.model.Address;
import internship.services.addressService.model.dto.AddressDTO;

public interface AddressDAO {
    Address findAddressById(Long id);

    Address updateAddress(Long id, AddressDTO addressDTO);

    Address createAddress(AddressDTO addressDTO);

    void removeAddress(Long id);
}
