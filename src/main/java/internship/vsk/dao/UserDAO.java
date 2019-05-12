package internship.vsk.dao;

import internship.vsk.models.User;

public interface UserDAO {
    /**
     * Returns user with specified Id
     * @param id
     * @return
     */
    User findUserById(long id);

    /**
     * Replaces user with specified id by another user
     * @param id
     * @param user
     */
    void updateUser(long id, User user);

    /**
     * Creates or replaces user with specified id
     * @param id
     * @param user
     */
    void createUser(long id, User user);

    /**
     * Drops user with specified id from storage
     * @param id
     */
    void removeUser(long id);
}
