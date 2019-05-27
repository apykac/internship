package internship.services.addressService;

import internship.dao.addressDAO.AddressDAO;
import internship.models.addressModel.Address;
import internship.services.addressService.response.AddressServiceResponse;
import internship.validators.addressValidator.IAddressValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class AddressServiceImpl implements AddressService {

    private final AddressServiceResponse addressServiceResponse = new AddressServiceResponse();

    private IAddressValidator addressValidator;
    private AddressDAO addressDAO;

    void setUserValidator(IAddressValidator addressValidator) {
        this.addressValidator = addressValidator;
    }

    void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    @GET
    @Path("/addresses/{id}/")
    public Response getAddress(@PathParam("id") Long id) {

        if (isServicesUp())
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(addressServiceResponse).build();

        Address addressFromDB = addressDAO.findAddressById(id);
        if (addressFromDB == null)
            return Response.status(Response.Status.NO_CONTENT).entity("Адресс с данным ID не найден").build();
        else
            return Response.ok().type("application/xml").entity(addressFromDB).build();
    }

    @POST
    @Path("/addresses/")
    public Response addAddress(Address address) {

        if (isServicesUp())
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(addressServiceResponse).build();

        if (addressValidator.isValid(address)) {
            Address newAddress = addressDAO.createAddress(address);
            return Response.ok().type("application/xml").entity(newAddress).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(addressValidator.getMessageError()).build();
        }
    }

    @PUT
    @Path("/addresses/{id}/")
    public Response updateAddress(@PathParam("id") Long id, Address address) {

        if (isServicesUp())
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(addressServiceResponse).build();

        if (addressValidator.isValid(address)) {
            Address updatedAddress = addressDAO.updateAddress(id, address);
            return Response.ok().type("application/xml").entity(updatedAddress).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(addressValidator.getMessageError()).build();
        }
    }

    @DELETE
    @Path("/addresses/{id}/")
    public Response deleteAddress(@PathParam("id") Long id) {

        if (isServicesUp())
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(addressServiceResponse).build();

        addressDAO.removeAddress(id);
        return Response.ok().build();
    }

    private boolean isServicesUp() {
        return (!(isAddressValidationUp() &
                isAddressDAOUp()));
    }

    private boolean isAddressValidationUp() {
        if (addressValidator == null) {
            addressServiceResponse.setAddressValidator("Валидация невозможна, так как сервис, предоставляемый бандлом AddressValidator не запущен");
            return false;
        } else {
            if (!addressValidator.isUserDAOUp()) {
                addressServiceResponse.setUserDAO("Работа с базой данных невозможна, так как сервис, предоставляемый бандлом UserDAO не запущен");
                return false;
            } else
                return true;
        }
    }

    private boolean isAddressDAOUp() {
        if (addressDAO == null) {
            addressServiceResponse.setAddressDAO("Работа с базой данных невозможна, так как сервис, предоставляемый бандлом AddressDAO не запущен");
            return false;
        } else {
            if (!addressDAO.isConnectorUp()) {
                addressServiceResponse.setDBConnector("Подключение к базе данных невозможно, так как сервис, предоставляемый бандлом DBConnector не запущен");
                return false;
            } else
                return true;
        }
    }

}
