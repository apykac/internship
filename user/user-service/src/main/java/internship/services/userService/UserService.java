package internship.services.userService;

import internship.models.userModel.User;

import javax.ws.rs.core.Response;

interface UserService {

	Response getUser(Long id);

	Response updateUser(Long id, User user);

	Response addUser(User user);

	Response deleteUser(Long id);
}
