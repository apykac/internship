package internship.dao.userDAO;

import internship.models.userModel.User;

import java.util.HashMap;
import java.util.Map;

public class UserHashMapDAO implements UserDAO {
    private static UserHashMapDAO dao;
    private final Map<Long, User> users = new HashMap<>();
    private Long currentId = 1L;

    public static UserDAO getInstance() {
        if (dao == null) {
            dao = new UserHashMapDAO();
            User vanya = new User("Ivan", "Rybachenko", "Alexandrovich", "15-05-1999", 1234567890L, 20000L);
            dao.createUser(vanya);
            User pushkin = new User("Alexandr", "Pushkin", "Sergeevich", "26-05-1799", 1234567890L, 20000L);
            dao.createUser(pushkin);
        }
        return dao;
    }

    @Override
    public User findUserById(Long id) {
        return users.get(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        User userFromMap = users.get(id);
        if (userFromMap != null) {
            users.put(id, user);
            return user;
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        user.setId(currentId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void removeUser(Long id) {
        users.remove(id);
    }

    @Override
    public boolean isConnectorUp() {
        return true;
    }
}
