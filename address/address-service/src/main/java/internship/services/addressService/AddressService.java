package internship.services.addressService;

import internship.models.addressModel.Address;

import javax.ws.rs.core.Response;

interface AddressService {

	Response getAddress(Long id);

	Response addAddress(Address address);

	Response updateAddress(Long id, Address address);

	Response deleteAddress(Long id);
}
