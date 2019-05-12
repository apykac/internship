package com.vsk.task.service.impl;

import com.vsk.task.dao.AddressDao;
import com.vsk.task.model.Address;
import com.vsk.task.service.AddressService;
import com.vsk.task.utils.AddressValidator;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class AddressServiceImpl implements AddressService {

    AddressDao addressDao=new AddressDao();



    @GET
    @Path("/addresses/{userId}/")
    public ArrayList<Address> getAddress(@PathParam("userId") String userId) {
        long idNumber = Long.parseLong(userId);
        ArrayList<Address> addresses=addressDao.getAddressByUserId(idNumber);
        return addresses;
    }

    @POST
    @Path("/addresses/")
    public Response addAddress(Address address) {
        AddressValidator addressValidator=new AddressValidator(address);
        if(addressValidator.isValid()){
            Address newAddress=addressDao.createAddress(address);
            return Response.ok().type("application/xml").entity(newAddress).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).entity(addressValidator.getMessageError()).build();
        }
    }
}
