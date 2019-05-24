package internship.sort.addressSort;

import internship.models.addressModel.Address;

import java.util.List;

public interface IAddressSort {
    void initList();
    List<Address> sort(Address address);
}
