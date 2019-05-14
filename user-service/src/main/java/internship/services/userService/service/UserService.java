package internship.services.userService.service;

import internship.services.userService.model.User;
import internship.services.userService.model.dto.UserDTO;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface UserService {

    User getUser(@PathParam("id") Long id);

    Response updateUser(@PathParam("id") Long id, UserDTO user);

    Response addUser(UserDTO user);

    Response deleteUser(@PathParam("id") Long id);
}
