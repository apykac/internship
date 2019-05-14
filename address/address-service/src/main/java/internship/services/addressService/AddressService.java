package internship.services.addressService;

import internship.models.addressModel.Address;
import internship.models.addressModel.dto.AddressDTO;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface AddressService {

	Address getAddress(@PathParam("id") Long id);

	Response addAddress(AddressDTO address);

	Response updateAddress(@PathParam("id") Long id, AddressDTO address);

	Response deleteAddress(@PathParam("id") Long id);
}
