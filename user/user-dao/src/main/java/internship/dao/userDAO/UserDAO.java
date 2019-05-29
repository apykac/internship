package internship.dao.userDAO;

import internship.models.userModel.User;

public interface UserDAO {
    User findUserById(Long id);

    User findUserByPassport(Long passport);

    User updateUser(Long passport, User user);

    User createUser(User user);

    void removeUser(Long passport);

    boolean isConnectorUp();
}
