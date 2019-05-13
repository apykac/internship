package com.vsk.task.service;

import com.vsk.task.model.User;
import com.vsk.task.model.dto.UserDTO;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface UserService {

    User getUser(@PathParam("id") Long id);

    Response updateUser(@PathParam("id") Long id, UserDTO user);

    Response addUser(UserDTO user);

    Response deleteUser(@PathParam("id") Long id);
}
