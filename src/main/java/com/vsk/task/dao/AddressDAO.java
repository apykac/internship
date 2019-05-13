package com.vsk.task.dao;

import com.vsk.task.model.Address;
import com.vsk.task.model.dto.AddressDTO;

public interface AddressDAO {
    Address findAddressById(Long id);

    Address updateAddress(Long id, AddressDTO addressDTO);

    Address createAddress(AddressDTO addressDTO);

    void removeAddress(Long id);
}
