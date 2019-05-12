package com.vsk.task.service;

import com.vsk.task.model.User;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface UserService {

    User getUser(@PathParam("id") String id);

    Response updateUser(User user);

    Response addUser(User user);

    Response deleteUser(@PathParam("id") String id);
}
