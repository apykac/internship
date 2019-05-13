package com.vsk.task.dao;

import com.vsk.task.model.Address;

import java.util.HashMap;
import java.util.Map;

public class AddressHashMapDAO implements AddressDAO {
    private static AddressHashMapDAO dao;
    private Map<Long, Address> addresses = new HashMap<>();

    public static AddressDAO getInstance() {
        if (dao == null) {
            dao = new AddressHashMapDAO();
        }
        return dao;
    }

    @Override
    public Address findAddressById(long id) {
        return addresses.get(id);
    }

    @Override
    public Address updateAddress(long id, Address address) {
        return addresses.put(id, address);
    }

    @Override
    public Address createAddress(long id, Address address) {
        return addresses.put(id, address);
    }

    @Override
    public void removeAddress(long id) {
        addresses.remove(id);
    }
}
