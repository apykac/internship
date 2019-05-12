package com.vsk.task.service;

import com.vsk.task.model.Address;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


public interface AddressService {

    ArrayList<Address> getAddress(@PathParam("addressId") String addressId);

    Response addAddress(Address address);
}
