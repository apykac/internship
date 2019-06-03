package internship.services.userService;

import internship.models.userModel.User;
import internship.models.userModel.Users;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

interface UserService {

    /**
     * Получить пользователя по его ноемеру пасспорта.
     *
     * @param passport Номер пасспорта запрашиваемого пользователя
     * @return Возвращает искомого пользователя либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @GET
    @Path("/users/{passport}/")
    @Produces("application/xml")
    Response getUser(@PathParam("passport") Long passport);

    /**
     * Заменить пользователя с указанным номером пасспорта.
     *
     * @param passport Номер пасспорта пользователя, который будет замещён
     * @param user     Новое значение пользователя в формате XML
     * @return Возвращает отредактированного пользователя либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @PUT
    @Path("/users/{passport}/")
    @Produces("application/xml")
    Response updateUser(@PathParam("passport") Long passport, User user);

    /**
     * Создать нового пользователя.
     *
     * @param user Пользователь для создания в формате XML.
     * @return Возвращает созданного пользователя либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @POST
    @Path("/users/")
    @Produces("application/xml")
    Response addUser(User user);

    //TODO: Сделать чтоб что-нибудь возвращал

    /**
     * Удалить пользователя с указанным номером пасспорта.
     *
     * @param passport Номер пасспорта пользователя, который будет удалён
     * @return Возвращает ничего либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @DELETE
    @Path("/users/{passport}/")
    Response deleteUser(Long passport);


    /**
     * Отфильтровать пользователей по доходам.
     *
     * @param users Пользователи для фильтрации
     * @return Возвращает отфильтрованный список либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @POST
    @Path("/users/filter/")
    @Produces("application/xml")
    Response filterUser(Users users);
}
