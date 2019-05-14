package internship.services.addressService.service;

import internship.services.addressService.model.Address;
import internship.services.addressService.model.dto.AddressDTO;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public interface AddressService {

    Address getAddress(@PathParam("id") Long id);

    Response addAddress(AddressDTO address);

    Response updateAddress(@PathParam("id") Long id, AddressDTO address);

    Response deleteAddress(@PathParam("id") Long id);
}
