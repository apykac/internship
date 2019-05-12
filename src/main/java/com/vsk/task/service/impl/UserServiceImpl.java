package com.vsk.task.service.impl;


import com.vsk.task.dao.UserDao;
import com.vsk.task.model.User;
import com.vsk.task.service.UserService;
import com.vsk.task.utils.UserValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/userservice")
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDao();

    @GET
    @Path("/users/{id}/")
    @Produces("application/xml")
    public User getUser(@PathParam("id") String id) {
        long idNumber = Long.parseLong(id);
        User c = userDao.getUser(idNumber);
        return c;
    }

    @PUT
    @Path("/users/")
    public Response updateUser(User user) {
        return null;
    }

    @POST
    @Path("/users/")
    public Response addUser(User user) {
        UserValidator userValidator=new UserValidator(user);
        if(userValidator.isValid()){
            User newUser=userDao.createUser(user);
            return Response.ok().type("application/xml").entity(newUser).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).entity(userValidator.getErrorMessage()).build();
        }
    }

    @DELETE
    @Path("/users/{id}/")
    public Response deleteUser(@PathParam("id") String id) {
        return null;
    }
}
