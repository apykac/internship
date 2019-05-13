package com.vsk.task.dao;

import com.vsk.task.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserHashMapDAO implements UserDAO {
    private static UserHashMapDAO dao;
    private Map<Long, User> users = new HashMap<>();

    public static UserDAO getInstance() {
        if (dao == null) {
            dao = new UserHashMapDAO();
        }
        return dao;
    }

    @Override
    public User findUserById(long id) {
        return users.get(id);
    }

    @Override
    public User updateUser(long id, User user) {
        return users.put(id, user);
    }

    @Override
    public User createUser(long id, User user) {
        return users.put(id, user);
    }

    @Override
    public void removeUser(long id) {
        users.remove(id);
    }
}
