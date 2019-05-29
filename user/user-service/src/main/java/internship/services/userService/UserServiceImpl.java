package internship.services.userService;

import internship.dao.userDAO.UserDAO;
import internship.models.userModel.User;
import internship.services.userService.response.UserServiceResponse;
import internship.validators.userValidator.IUserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * REST-интерфейс для работы с сущностью пользователь
 */
public class UserServiceImpl implements UserService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final UserServiceResponse userServiceResponse = new UserServiceResponse();

	private IUserValidator userValidator;
	private UserDAO userDAO;

	void setUserValidator(IUserValidator userValidator) {
		this.userValidator = userValidator;
	}

	void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * Получить пользователя, указав его номер паспорта.
	 * Возможные ошибки:
	 * 1) Пользователь с указанным номером паспорта не найден
	 * @param passport Номер паспорта запрашиваемого пользователя
	 * @return Возвращает информацию об указанном пользователе либо сообщение об ошибке.
	 */
	@GET
	@Path("/users/{passport}/")
	public Response getUser(@PathParam("passport") Long passport) {
		log.info("GET|GetUser invoked. userId="+passport);

		if (isServicesUp()) {
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(userServiceResponse).build();
		}

		log.info("LOG get user " + passport);
		User userFromDB = userDAO.findUserByPassport(passport);
		if (userFromDB == null) {
			return Response.status(Response.Status.NO_CONTENT).entity("Пользователь с указанным номером паспорта не найден").build();
		}
		else {
			return Response.ok().type("application/xml").entity(userFromDB).build();
		}
	}

	/**
	 * Перезаписывает пользователя с указанным id.
	 * Возможные ошибки:
	 * 1) Ошибки валидации
	 * 2) База данных недоступна
	 * @param id Id обновляемого пользователя
	 * @param user Новый экземпляр пользователя
	 * @return Возвращает обновлённый экземпляр пользователя либо сообщение об ошибке.
	 */
	@PUT
	@Path("/users/{id}/")
	public Response updateUser(@PathParam("id") Long id, User user) {
		log.info("PUT|UpdateUser invoked. userId="+id);

		if (isServicesUp())
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(userServiceResponse).build();

		if (userValidator.isValid(user)) {
			User updatedUser = userDAO.updateUser(id, user);
			return Response.ok().type("application/xml").entity(updatedUser).build();
		} else
			return Response.status(Response.Status.BAD_REQUEST).entity(userValidator.getErrorMessage()).build();
	}

	/**
	 * Создаёт нового пользователя.
	 * Возможные ошибки:
	 * 1) Ошибки валидации
	 * 2) База данных недоступна
	 * @param user Новый пользователь
	 * @return Возвращает созданного пользователя.
	 */
	@POST
	@Path("/users/")
	public Response addUser(User user) {
		log.info("POST|AddtUser invoked.");
		if (isServicesUp())
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(userServiceResponse).build();

		if (userValidator.isValid(user)) {
			User newUser = userDAO.createUser(user);
			return Response.ok().type("application/xml").entity(newUser).build();
		} else
			return Response.status(Response.Status.BAD_REQUEST).entity(userValidator.getErrorMessage()).build();
	}

	/**
	 * Удаляет пользователя с указанным id.
	 * Возможные ошибки:
	 * 1) База данных недоступна
	 * @param id Id удаляемого пользователя
	 * @return Возвращает ok или сообщение об ошибке.
	 */
	@DELETE
	@Path("/users/{id}/")
	public Response deleteUser(@PathParam("id") Long id) {
		log.info("DELETE|DeleteUser invoked. userId="+id);
		if (isServicesUp())
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(userServiceResponse).build();

		userDAO.removeUser(id);
		return Response.ok().build();
	}

	private boolean isServicesUp() {
		return (!(isUserDAOUp() &
				isUserValidationUp()));
	}

	private boolean isUserValidationUp() {
		if (userValidator == null) {
			userServiceResponse.setUserValidator("Валидация невозможна, так как сервис, предоставляемый бандлом UserValidator не запущен");
			return false;
		} else
			return true;
	}

	private boolean isUserDAOUp() {
		if (userDAO == null) {
			userServiceResponse.setUserDAO("Работа с базой данных невозможна, так как сервис, предоставляемый бандлом UserDAO не запущен");
			return false;
		} else {
			if (!userDAO.isConnectorUp()) {
				userServiceResponse.setDBConnector("Подключение к базе данных невозможно, так как сервис, предоставляемый бандлом DBConnector не запущен");
				return false;
			} else
				return true;
		}
	}
}
