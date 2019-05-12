package internship.vsk.dao;

import internship.vsk.models.User;

import java.util.HashMap;
import java.util.Map;

public class UserHashMapDAO implements UserDAO {
    private static UserHashMapDAO dao;

    private Map<Long, User> users = new HashMap<Long, User>();

    private UserHashMapDAO(){

    }

    public static UserDAO getInstance() {
        if (dao==null){
            dao = new UserHashMapDAO();
        }
        return dao;
    }

    public User findUserById(long id) {
        User user = users.get(id);
        return user;
    }

    public void updateUser(long id, User user) {
        users.put(id, user);
    }

    public void createUser(long id, User user) {
        users.put(id, user);
    }

    public void removeUser(long id) {
        users.remove(id);
    }
}
