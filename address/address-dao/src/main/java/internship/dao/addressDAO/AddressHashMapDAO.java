package internship.dao.addressDAO;

import internship.models.addressModel.Address;
import internship.models.addressModel.dto.AddressDTO;

import java.util.HashMap;
import java.util.Map;

public class AddressHashMapDAO implements AddressDAO {
	private static AddressHashMapDAO dao;
	private Map<Long, Address> addresses = new HashMap<>();
	private Long currentId = 1L;

	public static AddressDAO getInstance() {
		if (dao == null) {
			dao = new AddressHashMapDAO();
			AddressDTO vanya = new AddressDTO(1, "Russia", "Promyshlennaya", "Krasnoarmeyskaya", "2", "2");
			dao.createAddress(vanya);
			AddressDTO pushkin = new AddressDTO(2, "Russia", "Saint Petersburg", "", "", "");
			dao.createAddress(pushkin);
		}
		return dao;
	}

	@Override
	public Address findAddressById(Long id) {
		return addresses.get(id);
	}

	@Override
	public Address updateAddress(Long id, AddressDTO addressDTO) {
		Address addressFromDB = findAddressById(id);
		addressFromDB.setUserId(addressDTO.getUserId());
		addressFromDB.setCountry(addressDTO.getCountry());
		addressFromDB.setCity(addressDTO.getCity());
		addressFromDB.setStreet(addressDTO.getStreet());
		addressFromDB.setHouseNumber(addressDTO.getHouseNumber());
		addressFromDB.setApartmentNumber(addressDTO.getApartmentNumber());

		return addressFromDB;
	}

	@Override
	public Address createAddress(AddressDTO addressDTO) {
		Address address = new Address();
		address.setId(currentId++);
		address.setUserId(addressDTO.getUserId());
		address.setCountry(addressDTO.getCountry());
		address.setCity(addressDTO.getCity());
		address.setStreet(addressDTO.getStreet());
		address.setHouseNumber(addressDTO.getHouseNumber());
		address.setApartmentNumber(addressDTO.getApartmentNumber());

		addresses.put(address.getId(), address);
		return address;
	}

	@Override
	public void removeAddress(Long id) {
		addresses.remove(id);
	}
}
