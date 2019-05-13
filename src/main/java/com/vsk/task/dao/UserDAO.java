package com.vsk.task.dao;

import com.vsk.task.model.User;
import com.vsk.task.model.dto.UserDTO;

public interface UserDAO {
    User findUserById(Long id);

    User updateUser(Long id, UserDTO userDTO);

    User createUser(UserDTO userDTO);

    void removeUser(Long id);
}
