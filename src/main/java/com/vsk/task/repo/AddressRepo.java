package com.vsk.task.repo;

import com.vsk.task.model.Address;

import java.util.HashMap;

public class AddressRepo {
    private static HashMap<Long, Address> addresses = new HashMap<>();

    public static HashMap<Long, Address> getAddresses() {
        return addresses;
    }
}
