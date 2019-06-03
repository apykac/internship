package internship.services.addressService;

import internship.dao.addressDAO.AddressDAO;
import internship.models.addressModel.Address;
import internship.models.addressModel.Addresses;
import internship.services.addressService.response.AddressServiceResponse;
import internship.services.addressSort.IAddressSort;
import internship.validators.addressValidator.IAddressValidator;
import internship.validators.addressValidator.models.ValidationResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class AddressServiceImpl implements AddressService {

    private final AddressServiceResponse addressServiceResponse = new AddressServiceResponse();

    private IAddressValidator addressValidator;
    private AddressDAO addressDAO;
    private IAddressSort addressSort;

    void setUserValidator(IAddressValidator addressValidator) {
        this.addressValidator = addressValidator;
    }

    void setAddressDAO(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    void setAddressSort(IAddressSort addressSort){
        this.addressSort=addressSort;
    }

    @POST
    @Path("/addresses/sort/")
    public Response sortAddress(Addresses addresses){
        List<Address> sortedAddressList;
        ValidationResult vr = addressValidator.validate(addresses.getAddresses());
        if(vr.isValid()){
            sortedAddressList=addressSort.sort(addresses.getAddresses());
        }else {
            return Response.status(Response.Status.BAD_REQUEST).entity(vr).build();
        }
        Addresses sortedListWrapper=new Addresses();
        sortedListWrapper.setAddresses(sortedAddressList);

        return Response.ok().type("application/xml").entity(sortedListWrapper).build();
    }


    @GET
    @Path("/addresses/{id}/")
    public Response getAddress(@PathParam("id") Long id) {

        if (isServicesDown())
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(addressServiceResponse).build();

        Address addressFromDB = addressDAO.findAddressById(id);
        if (addressFromDB == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Адресс с данным ID не найден").build();
        else
            return Response.ok().type("application/xml").entity(addressFromDB).build();
    }

    @POST
    @Path("/addresses/")
    public Response addAddress(Address address) {

        if (isServicesDown())
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(addressServiceResponse).build();
        ValidationResult vr = addressValidator.validate(address);
        if (vr.isValid()) {
            Address newAddress = addressDAO.createAddress(address);
            return Response.ok().type("application/xml").entity(newAddress).build();
        } else {
            return Response.status(Response.Status.OK).entity(vr).build();
        }
    }

    @PUT
    @Path("/addresses/{id}/")
    public Response updateAddress(@PathParam("id") Long id, Address address) {

        if (isServicesDown())
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(addressServiceResponse).build();

        ValidationResult vr = addressValidator.validate(address);
        if (vr.isValid()) {
            Address updatedAddress = addressDAO.updateAddress(id, address);
            return Response.ok().type("application/xml").entity(updatedAddress).build();
        } else {
            return Response.status(Response.Status.OK).entity(vr).build();
        }
    }

    @DELETE
    @Path("/addresses/{id}/")
    public Response deleteAddress(@PathParam("id") Long id) {

        if (isServicesDown())
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(addressServiceResponse).build();

        addressDAO.removeAddress(id);
        return Response.ok().build();
    }

    private boolean isServicesDown() {
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
