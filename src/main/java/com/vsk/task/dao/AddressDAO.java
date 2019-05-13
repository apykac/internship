package com.vsk.task.dao;

import com.vsk.task.model.Address;

public interface AddressDAO {
    Address findAddressById(long id);

    Address updateAddress(long id, Address address);

    Address createAddress(long id, Address address);

    void removeAddress(long id);
}
