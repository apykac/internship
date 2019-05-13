package com.vsk.task.service.impl;

import com.vsk.task.dao.AddressDAO;
import com.vsk.task.dao.AddressHashMapDAO;
import com.vsk.task.model.Address;
import com.vsk.task.service.AddressService;
import com.vsk.task.utils.AddressValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/addressservice")
public class AddressServiceImpl implements AddressService {

    private AddressDAO addressDao = AddressHashMapDAO.getInstance();

    @GET
    @Path("/addresses/{id}/")
    public Address getAddress(@PathParam("id") Long id) {
        return addressDao.findAddressById(id);
    }

    @POST
    @Path("/addresses/")
    public Response addAddress(Address address) {
        AddressValidator addressValidator = new AddressValidator(address);
        if (addressValidator.isValid()) {
            Address newAddress = addressDao.createAddress(address.getId(), address);
            return Response.ok().type("application/xml").entity(newAddress).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(addressValidator.getMessageError()).build();
        }
    }

    @PUT
    @Path("/addresses/{id}/")
    public Response updateAddress(@PathParam("id") Long id, Address address) {
        AddressValidator addressValidator = new AddressValidator(address);
        if (addressValidator.isValid()) {
            Address newAddress = addressDao.updateAddress(id, address);
            return Response.ok().type("application/xml").entity(newAddress).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(addressValidator.getMessageError()).build();
        }
    }

    @DELETE
    @Path("/addresses/{id}/")
    public Response deleteAddress(@PathParam("id") Long id) {
        addressDao.removeAddress(id);
        return Response.ok().build();
    }

}
