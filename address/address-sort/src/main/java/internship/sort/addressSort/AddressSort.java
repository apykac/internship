package internship.sort.addressSort;

import internship.models.addressModel.Address;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddressSort implements IAddressSort {

    List<Address> addresses=new ArrayList<>();
    public void initList(){
        addresses=new ArrayList<>();
    }
    //FIXME pls pls
    public List<Address> sort(Address address) {
        addresses.add(address);
        addresses.sort(Comparator.comparing(Address::getRegion));
        return addresses;
    }
}
