package internship.services.addressService;

import internship.models.addressModel.Address;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface AddressService {

    Response getAddress(@PathParam("id") Long id);

    Response addAddress(Address address);

    Response updateAddress(@PathParam("id") Long id, Address address);

    Response deleteAddress(@PathParam("id") Long id);
}
