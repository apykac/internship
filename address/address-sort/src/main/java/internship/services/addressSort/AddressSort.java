package internship.services.addressSort;

import internship.models.addressModel.Address;

import java.util.Comparator;
import java.util.List;

public class AddressSort implements IAddressSort {

    public List<Address> sort(List<Address> address) {
        address.sort(Comparator.comparing(Address::getRegion));
        return address;
    }
}
