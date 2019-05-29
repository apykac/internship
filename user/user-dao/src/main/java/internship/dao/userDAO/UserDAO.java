package internship.dao.userDAO;

import internship.models.userModel.User;

public interface UserDAO {
    User findUserById(Long id);

    User updateUser(Long id, User user);

    User createUser(User user);

    void removeUser(Long id);

    boolean isConnectorUp();
}
