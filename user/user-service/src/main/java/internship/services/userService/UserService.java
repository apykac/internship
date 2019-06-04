package internship.services.userService;

import internship.models.userModel.User;
import internship.models.userModel.Users;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

interface UserService {

    /**
     * Получить пользователя по его ноемеру паспорта.
     *
     * @param passport Номер паспорта запрашиваемого пользователя
     * @return Возвращает искомого пользователя либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @GET
    @Path("/users/{passport}/")
    @Produces("application/xml")
    Response getUser(@PathParam("passport") Long passport);

    /**
     * Заменить пользователя с указанным номером паспорта.
     *
     * @param passport Номер паспорта пользователя, который будет замещён
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


    /**
     * Удалить пользователя с указанным номером паспорта.
     *
     * @param passport Номер паспорта пользователя, который будет удалён
     * @return Возвращает ничего либо сообщение об ошибке. Выходные данные в формате XML.
     */
    @DELETE
    @Path("/users/{passport}/")
    Response deleteUser(@PathParam("passport") Long passport);


    /**
     * Сервис фильтрации пользователей чей доход ниже 20000
     * Этапы фильтрации:
     * 1) Проверяем поле дохода пользователя и удаляем тех, кто не соответствует требованиям
     * 2) Сортируем оставшихся пользователей по уменьшению дохода
     * @return Возвращает отфильтрованный список пользователей.
     */
    @POST
    @Path("/users/filter/")
    @Produces("application/xml")
    Response filterUser(Users users);
}
