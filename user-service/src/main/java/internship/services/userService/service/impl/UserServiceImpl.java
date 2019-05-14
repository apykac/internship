package internship.services.userService.service.impl;

import internship.services.userService.dao.UserDAO;
import internship.services.userService.dao.UserHashMapDAO;
import internship.services.userService.model.User;
import internship.services.userService.model.dto.UserDTO;
import internship.services.userService.service.UserService;
import internship.services.userService.utils.UserValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/userservice")
public class UserServiceImpl implements UserService {

    private UserDAO userDao = UserHashMapDAO.getInstance();

    @GET
    @Path("/users/{id}/")
    @Produces("application/xml")
    public User getUser(@PathParam("id") Long id) {
        return userDao.findUserById(id);
    }

    @PUT
    @Path("/users/{id}/")
    public Response updateUser(@PathParam("id") Long id, UserDTO user) {
        UserValidator userValidator = new UserValidator(user);
        if (userValidator.isValid()) {
            User updatedUser = userDao.updateUser(id, user);
            return Response.ok().type("application/xml").entity(updatedUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(userValidator.getErrorMessage()).build();
        }
    }

    @POST
    @Path("/users/")
    public Response addUser(UserDTO user) {
        UserValidator userValidator = new UserValidator(user);
        if (userValidator.isValid()) {
            User newUser = userDao.createUser(user);
            return Response.ok().type("application/xml").entity(newUser).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(userValidator.getErrorMessage()).build();
        }
    }

    @DELETE
    @Path("/users/{id}/")
    public Response deleteUser(@PathParam("id") Long id) {
        userDao.removeUser(id);
        return Response.ok().build();
    }
}
