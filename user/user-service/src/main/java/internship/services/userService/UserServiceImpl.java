package internship.services.userService;

import internship.dao.userDAO.UserDAO;
import internship.models.userModel.User;
import internship.models.userModel.Users;
import internship.services.userFilter.IUserFilter;
import internship.services.userService.response.ErrorResponse;
import internship.services.userService.response.UserServiceResponse;
import internship.validators.userValidator.IUserValidator;
import internship.validators.userValidator.models.ValidationResult;

import javax.ws.rs.core.Response;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserServiceResponse userServiceResponse = new UserServiceResponse();

    private IUserValidator userValidator;
    private UserDAO userDAO;
    private IUserFilter userFilter;

    void setUserValidator(IUserValidator userValidator) {
        this.userValidator = userValidator;
    }

    void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    void setUserFilter(IUserFilter userFilter) {
        this.userFilter = userFilter;
    }

    public Response filterUser(Users users) {
        List<User> filteredUserList;
        userValidator.removeInvalidUsers(users.getUsers());
        if (users.getUsers().size()!=0) {
            filteredUserList = userFilter.filter(users.getUsers());
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("<Error>Не было получено ни одного корректного адреса для сортировки</Error>")
                    .build();
        }
        Users filteredListWrapper = new Users();
        filteredListWrapper.setUsers(filteredUserList);

        return Response
                .ok()
                .entity(filteredListWrapper)
                .build();
    }

    public Response getUser(Long passport) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(userServiceResponse)
                    .build();
        }

        User userFromDB = userDAO.findUserByPassport(passport);
        if (userFromDB == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(ErrorResponse.NO_USER_WITH_SUCH_PASSPORT))
                    .build();
        } else {
            return Response
                    .ok()
                    .entity(userFromDB)
                    .build();
        }
    }

    public Response updateUser(Long passport, User user) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(userServiceResponse)
                    .build();
        }

        ValidationResult vr = userValidator.validate(user);
        if (!vr.isValid()) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(vr)
                    .build();
        }

        if (userDAO.findUserByPassport(passport) == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("<Error>Пользователь, которого вы пытаетесь отредактировтаь не существует</Error>")
                    .build();
        }
        if (!passport.equals(user.getPassportNumber()) && userDAO.findUserByPassport(user.getPassportNumber()) != null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("<Error>Номер пасспорта, который вы пытаетесь установить, уже занят</Error>")
                    .build();
        }

        User updatedUser = userDAO.updateUser(passport, user);
        return Response
                .ok()
                .entity(updatedUser)
                .build();
    }

    public Response addUser(User user) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(userServiceResponse)
                    .build();
        }

        ValidationResult vr = userValidator.validate(user);
        if (vr.isValid()) {
            User newUser = userDAO.createUser(user);
            return Response
                    .ok()
                    .entity(newUser)
                    .build();
        } else {
            return Response
                    .ok()
                    .entity(vr)
                    .build();
        }
    }

    public Response deleteUser(Long passport) {

        if (isServicesDown()) {
            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(userServiceResponse)
                    .build();
        }

        userDAO.removeUser(passport);
        return Response
                .ok()
                .build();
    }

    private boolean isServicesDown() {
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
