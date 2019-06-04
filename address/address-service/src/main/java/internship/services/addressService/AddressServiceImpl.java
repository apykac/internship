package internship.services.addressService;

import internship.dao.addressDAO.AddressDAO;
import internship.models.addressModel.Address;
import internship.models.addressModel.Addresses;
import internship.services.addressService.response.AddressServiceResponse;
import internship.services.addressService.response.ErrorResponse;
import internship.services.addressService.response.SortResponse;
import internship.services.addressSort.IAddressSort;
import internship.validators.addressValidator.IAddressValidator;
import internship.validators.addressValidator.models.ValidationResult;

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

    void setAddressSort(IAddressSort addressSort) {
        this.addressSort = addressSort;
    }

    public Response sortAddress(Addresses addresses) {
        List<Address> sortedAddressList;

        ValidationResult vr = addressValidator.validate(addresses.getAddresses());
        addressValidator.removeInvalidAddresses(addresses.getAddresses());
        if (addresses.getAddresses().size() != 0) {
            sortedAddressList = addressSort.sort(addresses.getAddresses());
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new SortResponse(vr,null))
                    .build();
        }
        Addresses sortedListWrapper = new Addresses();
        sortedListWrapper.setAddresses(sortedAddressList);

        return Response
                .ok()
                .entity(new SortResponse(vr,sortedListWrapper))
                .build();
    }

    public Response getAddress(Long id) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(addressServiceResponse)
                    .build();
        }

        Address addressFromDB = addressDAO.findAddressById(id);
        if (addressFromDB == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(ErrorResponse.NO_ADDRESS_WITH_SUCH_ID))
                    .build();
        } else {
            return Response
                    .ok()
                    .entity(addressFromDB)
                    .build();

        }
    }

    public Response getAddressesForUser(Long passport) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(addressServiceResponse)
                    .build();
        }

        Addresses addressesFromDB = addressDAO.findAddressesByUserPassport(passport);
        if (addressesFromDB == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(ErrorResponse.NO_ADDRESSES_WITH_SUCH_PASSPORT))
                    .build();
        } else {
            return Response
                    .ok()
                    .entity(addressesFromDB)
                    .build();

        }
    }

    public Response addAddress(Address address) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(addressServiceResponse)
                    .build();
        }

        ValidationResult vr = addressValidator.validate(address);
        if (vr.isValid()) {
            Address newAddress = addressDAO.createAddress(address);
            if (newAddress == null) {
                return Response
                        .ok()
                        .entity("<Error>Не удалось создать адрес.</Error>")
                        .build();
            }
            return Response
                    .ok()
                    .entity(newAddress)
                    .build();
        } else {
            return Response
                    .ok()
                    .entity(vr)
                    .build();
        }
    }

    public Response updateAddress(Long id, Address address) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(addressServiceResponse)
                    .build();
        }

        ValidationResult vr = addressValidator.validate(address);
        if (!vr.isValid()) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(vr)
                    .build();
        }

        if (addressDAO.findAddressById(address.getId()) == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("<Error>Адрес с указанным id не существует</Error>")
                    .build();
        }
        Address updatedAddress = addressDAO.updateAddress(id, address);
        return Response
                .ok()
                .entity(updatedAddress)
                .build();

    }

    public Response deleteAddress(Long id) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(addressServiceResponse)
                    .build();
        }

        addressDAO.removeAddress(id);

        return Response
                .ok()
                .build();
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
