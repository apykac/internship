package internship.services.userService.dao;

import internship.services.userService.model.User;
import internship.services.userService.model.dto.UserDTO;

public interface UserDAO {
    User findUserById(Long id);

    User updateUser(Long id, UserDTO userDTO);

    User createUser(UserDTO userDTO);

    void removeUser(Long id);
}
