package com.vsk.task.dao;

import com.vsk.task.model.Address;

import java.util.*;

public class AddressDao {
    Map<Long, Address> addressRepo=new HashMap<Long, Address>();
    long currentId=123;

    public Address createAddress(Address address){
        long nextId=currentId++;
        address.setId(nextId);
        addressRepo.put(nextId,address);
        return addressRepo.get(nextId);
    }

    public Address getAddress(long id){
        return addressRepo.get(id);
    }

    public ArrayList<Address> getAddressByUserId(long userId){
        ArrayList<Address> addresses=new ArrayList<Address>();
        for(Map.Entry<Long, Address> entry: addressRepo.entrySet()){
            Address value=entry.getValue();
            if(value.getUserId()==userId){
                addresses.add(value);
            }
        }
        return addresses;
    }
}
