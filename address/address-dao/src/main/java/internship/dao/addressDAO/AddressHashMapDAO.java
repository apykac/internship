package internship.dao.addressDAO;

import internship.models.addressModel.Address;

import java.util.HashMap;
import java.util.Map;

public class AddressHashMapDAO implements AddressDAO {
    private static AddressHashMapDAO dao;
    private Map<Long, Address> addresses = new HashMap<>();
    private Long currentId = 1L;

    public static AddressDAO getInstance() {
        if (dao == null) {
            dao = new AddressHashMapDAO();
            Address vanya = new Address(2L, "Russia", "Tomskaya oblast", "Promyshlennaya", "Krasnoarmeyskaya", "2", "2");
            dao.createAddress(vanya);
            Address pushkin = new Address(3L, "Russia", "Tomskaya oblast", "Saint Petersburg", "", "", "");
            dao.createAddress(pushkin);
        }
        return dao;
    }

    @Override
    public Address findAddressById(Long id) {
        return addresses.get(id);
    }

    @Override
    public Address updateAddress(Long id, Address address) {
        Address addressFromMap = addresses.get(id);
        if (addressFromMap != null) {
            addresses.put(id, address);
            return address;
        }
        return null;
    }

    @Override
    public Address createAddress(Address address) {
        address.setId(currentId++);
        addresses.put(address.getId(), address);
        return address;
    }

    @Override
    public void removeAddress(Long id) {
        addresses.remove(id);
    }

    @Override
    public boolean isConnectorUp() {
        return true;
    }
}
