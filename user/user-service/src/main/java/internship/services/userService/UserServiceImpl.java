package internship.services.userService;

import internship.dao.userDAO.UserDAO;
import internship.dao.userDAO.UserDatabaseDAO;
import internship.dao.userDAO.UserHashMapDAO;
import internship.models.userModel.User;
import internship.models.userModel.dto.UserDTO;
import internship.validators.userValidator.UserValidator;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class UserServiceImpl implements UserService {

    private UserDAO userDao = UserHashMapDAO.getInstance();

    @GET
    @Path("/users/{id}/")
    @Produces("application/xml")
    public User getUser(@PathParam("id") Long id) {
        System.out.println("get user " + id);
        return userDao.findUserById(id);
    }

    @GET
    @Path("/users2/{id}")
    public String getAddress(){
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://localhost:8181/cxf/userservice/users/1");
        try {
            client.executeMethod(method);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = null;
        try {
            s = method.getResponseBodyAsString();
        } catch (IOException e) {
            s = "error";
            e.printStackTrace();
        }
        return  s;
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
