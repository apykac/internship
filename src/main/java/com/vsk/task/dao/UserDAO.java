package com.vsk.task.dao;

import com.vsk.task.model.User;

public interface UserDAO {
    User findUserById(long id);

    User updateUser(long id, User user);

    User createUser(long id, User user);

    void removeUser(long id);
}
