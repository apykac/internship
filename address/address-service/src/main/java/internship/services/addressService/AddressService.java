package internship.services.addressService;

import internship.models.addressModel.Address;
import internship.models.addressModel.Addresses;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

interface AddressService {

    /**
     * Получить адрес по его id.
     *
     * @param id id запрашиваемого адреса
     * @return Возвращает искомый адрес либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @GET
    @Path("/addresses/{id}/")
    @Produces("application/xml")
    Response getAddress(@PathParam("id") Long id);

    /**
     * Получить адресы по номеру пасспорта пользователя.
     *
     * @param passport Номер пасспорта пользователя, для которого запрашиваются адресы
     * @return Возвращает искомый адрес либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @GET
    @Path("/addresses/list/{passport}/")
    @Produces("application/xml")
    Response getAddressesForUser(@PathParam("passport") Long passport);

    /**
     * Создать новый адрес.
     *
     * @param address Адрес для создания в формате XML.
     * @return Возвращает созданный адрес либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @POST
    @Path("/addresses/")
    @Produces("application/xml")
    Response addAddress(Address address);

    /**
     * Заменить адрес с указанным id.
     *
     * @param id      Id адреса, который будет замещён
     * @param address Новое значение адреса в формате XML
     * @return Возвращает созданный адрес либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @PUT
    @Path("/addresses/{id}/")
    @Produces("application/xml")
    Response updateAddress(@PathParam("id") Long id, Address address);

    //TODO: Сделать чтоб что-нибудь возвращал

    /**
     * Удалить адрес с указанным id.
     *
     * @param id Id адреса, который будет удалён
     * @return Возвращает ничего либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @DELETE
    @Path("/addresses/{id}/")
    @Produces("application/xml")
    Response deleteAddress(@PathParam("id") Long id);

    /**
     * Отсортировать адреса по регионам.
     *
     * @param addresses Адреса для сортировки
     * @return Возвращает отсортированный список либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @POST
    @Path("/addresses/sort/")
    @Produces("application/xml")
    Response sortAddress(Addresses addresses);
}
