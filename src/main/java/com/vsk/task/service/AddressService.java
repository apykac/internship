package com.vsk.task.service;

import com.vsk.task.model.Address;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public interface AddressService {

    Address getAddress(@PathParam("id") Long id);

    Response addAddress(Address address);

    Response updateAddress(@PathParam("id") Long id, Address address);

    Response deleteAddress(@PathParam("id") Long id);
}
