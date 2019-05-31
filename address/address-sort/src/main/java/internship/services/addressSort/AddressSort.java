package internship.services.addressSort;

import internship.models.addressModel.Address;

import java.util.Comparator;
import java.util.List;

public class AddressSort implements IAddressSort {

    public List<Address> sort(List<Address> address) {
        address.sort(Comparator.comparing(Address::getRegion));
        for(int i=0; i<address.size();i++){
            address.get(i).setId(i+1);
        }
        return address;
    }
}
