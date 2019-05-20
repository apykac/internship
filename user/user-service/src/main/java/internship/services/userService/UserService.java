package internship.services.userService;

import internship.models.userModel.User;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface UserService {

    Response getUser(@PathParam("id") Long id);

    Response updateUser(@PathParam("id") Long id, User user);

    Response addUser(User user);

    Response deleteUser(@PathParam("id") Long id);
}
