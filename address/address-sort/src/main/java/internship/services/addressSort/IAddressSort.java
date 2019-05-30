package internship.services.addressSort;

import internship.models.addressModel.Address;

import java.util.List;

public interface IAddressSort {
    List<Address> sort(List<Address> address);
}
