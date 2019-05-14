package internship.dao.userDAO;

import internship.models.userModel.User;
import internship.models.userModel.dto.UserDTO;

public interface UserDAO {
    User findUserById(Long id);

    User updateUser(Long id, UserDTO userDTO);

    User createUser(UserDTO userDTO);

    void removeUser(Long id);
}
