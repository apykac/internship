package internship.vsk.services;

import internship.vsk.dao.UserDAO;
import internship.vsk.dao.UserHashMapDAO;
import internship.vsk.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/userservice")
public class UserService{
    UserDAO dao =  UserHashMapDAO.getInstance();
    /**
     * This method is mapped to an HTTP GET of 'http://localhost:8181/cxf/crm/userservice/users/{id}'.  The value for
     * {id} will be passed to this message as a parameter, using the @PathParam annotation.
     * <p/>
     * The method returns a User object - for creating the HTTP response, this object is marshaled into XML using JAXB.
     * <p/>
     * For example: surfing to 'http://localhost:8181/cxf/crm/userservice/users/123' will show you the information of
     * user 123 in XML format.
     */
    @GET
    @Path("/users/{id}/")
    @Produces("application/xml")
    public User getUser(@PathParam("id") String id) {
        System.out.println("----invoking getUser, User id is: " + id);
        long idNumber = Long.parseLong(id);
        User c = dao.findUserById(idNumber);
        return c;

    }

    /**
     * Using HTTP PUT, we can can upload the XML representation of a user object.  This operation will be mapped
     * to the method below and the XML representation will get unmarshaled into a real User object using JAXB.
     * <p/>
     * The method itself just updates the user object in our local data map and afterwards uses the Reponse class to
     * build the appropriate HTTP response: either OK if the update succeeded (translates to HTTP Status 200/OK) or not
     * modified if the method failed to update a user object (translates to HTTP Status 304/Not Modified).
     * <p/>
     * Note how this method is using the same @Path value as our next method - the HTTP method used will determine which
     * method is being invoked.
     */
    @PUT
    @Path("/users/")
    public Response updateUser(User user) {
        System.out.println("----invoking updateUser, User name is: " + user.getName());
        User c = dao.findUserById(user.getId());
        Response r;
        if (c != null) {
            dao.updateUser(user.getId(), user);
            r = Response.ok().build();
        } else {
            r = Response.notModified().build();
        }

        return r;
    }

    /**
     * Using HTTP POST, we can add a new user to the system by uploading the XML representation for the user.
     * This operation will be mapped to the method below and the XML representation will get unmarshaled into a real
     * User object.
     * <p/>
     * After the method has added the user to the local data map, it will use the Response class to build the HTTP reponse,
     * sending back the inserted user object together with a HTTP Status 200/OK.  This allows us to send back the
     * new id for the user object to the client application along with any other data that might have been updated in
     * the process.
     * <p/>
     * Note how this method is using the same @Path value as our previous method - the HTTP method used will determine which
     * method is being invoked.
     */
    @POST
    @Path("/users/")
    public Response addUser(User user) {
        System.out.println("----invoking addUser, User name is: " + user.getName());

        dao.createUser(user.getId(), user);

        return Response.ok().type("application/xml").entity(user).build();
    }

    /**
     * This method is mapped to an HTTP DELETE of 'http://localhost:8181/cxf/crm/userservice/users/{id}'.  The value for
     * {id} will be passed to this message as a parameter, using the @PathParam annotation.
     * <p/>
     * The method uses the Response class to create the HTTP response: either HTTP Status 200/OK if the user object was
     * successfully removed from the local data map or a HTTP Status 304/Not Modified if it failed to remove the object.
     */
    @DELETE
    @Path("/users/{id}/")
    public Response deleteUser(@PathParam("id") String id) {
        System.out.println("----invoking deleteUser, User id is: " + id);
        long idNumber = Long.parseLong(id);
        User c = dao.findUserById(idNumber);

        Response r;
        if (c != null) {
            r = Response.ok().build();
            dao.removeUser(idNumber);
        } else {
            r = Response.notModified().build();
        }

        return r;
    }
}
