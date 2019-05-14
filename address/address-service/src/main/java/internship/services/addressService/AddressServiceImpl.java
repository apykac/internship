package internship.services.addressService;

import internship.dao.addressDAO.AddressDAO;
import internship.dao.addressDAO.AddressHashMapDAO;
import internship.models.addressModel.Address;
import internship.models.addressModel.dto.AddressDTO;
import internship.validators.addressValidator.response.AddressValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class AddressServiceImpl implements AddressService {

	private AddressDAO addressDao = AddressHashMapDAO.getInstance();

	@GET
	@Path("/addresses/{id}/")
	public Address getAddress(@PathParam("id") Long id) {
		return addressDao.findAddressById(id);
	}

	@POST
	@Path("/addresses/")
	public Response addAddress(AddressDTO address) {
		AddressValidator addressValidator = new AddressValidator(address);
		if (addressValidator.isValid()) {
			Address newAddress = addressDao.createAddress(address);
			return Response.ok().type("application/xml").entity(newAddress).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity(addressValidator.getMessageError()).build();
		}
	}

	@PUT
	@Path("/addresses/{id}/")
	public Response updateAddress(@PathParam("id") Long id, AddressDTO address) {
		AddressValidator addressValidator = new AddressValidator(address);
		if (addressValidator.isValid()) {
			Address updatedAddress = addressDao.updateAddress(id, address);
			return Response.ok().type("application/xml").entity(updatedAddress).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity(addressValidator.getMessageError()).build();
		}
	}

	@DELETE
	@Path("/addresses/{id}/")
	public Response deleteAddress(@PathParam("id") Long id) {
		addressDao.removeAddress(id);
		return Response.ok().build();
	}

}
