package internship.dao.addressDAO;

import internship.connectors.postgresConnector.IConnector;
import internship.models.addressModel.Address;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//FIXME Переписать запросы к базе, закрывать сессию (не ту, которая в универе, хотя, ту тоже лучше закрыть)
public class AddressDatabaseDAO implements AddressDAO {

	private IConnector connector;
	private Connection dbConnection;

	void setConnector(IConnector connector) {
		this.connector = connector;
		dbConnection = connector.getConnection();
	}

	@Override
	public Address findAddressById(Long id) {
		try {
			if (dbConnection == null)
				return null;

			PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM addresses WHERE id = ?");
			preparedStatement.setLong(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return new Address(
						resultSet.getLong("id"),
						resultSet.getLong("userId"),
						resultSet.getString("country"),
						resultSet.getString("region"),
						resultSet.getString("city"),
						resultSet.getString("street"),
						resultSet.getString("houseNumber"),
						resultSet.getString("apartmentNumber")
				);
			}
			dbConnection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public Address updateAddress(Long id, Address address) {
		try {
			if (dbConnection == null)
				return null;

			PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE addresses " +
					"SET \"userId\" = ?, country = ?, region = ?, city = ?, street = ?, \"houseNumber\" = ?, \"apartmentNumber\" = ? " +
					"WHERE id = ? RETURNING id, \"userId\", country, region, city, street, \"houseNumber\", \"apartmentNumber\"");
			setStatement(address, preparedStatement);
			preparedStatement.setLong(8, id);

			Address addressFromResultSet = getResultSet(preparedStatement.executeQuery(), address);
			dbConnection.close();
			return addressFromResultSet;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public Address createAddress(Address address) {
		try {
			if (dbConnection == null)
				return null;

			PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO public.addresses " +
					"(\"userId\", country, region, city, street, \"houseNumber\", \"apartmentNumber\") " +
					"VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id, \"userId\", country, region, city, street, \"houseNumber\", \"apartmentNumber\"");
			setStatement(address, preparedStatement);

			Address addressFromResultSet = getResultSet(preparedStatement.executeQuery(), address);
			dbConnection.close();
			return addressFromResultSet;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public void removeAddress(Long id) {
		try {
			if (dbConnection == null)
				throw new ConnectException();

			PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM addresses WHERE id = ?");
			preparedStatement.setLong(1, id);
			preparedStatement.execute();

			dbConnection.close();
		} catch (SQLException | ConnectException e) {
			System.out.println(e.getMessage());
		}
	}

	private void setStatement(Address address, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setLong(1, address.getUserId());
		preparedStatement.setString(2, address.getCountry());
		preparedStatement.setString(3, address.getRegion());
		preparedStatement.setString(4, address.getCity());
		preparedStatement.setString(5, address.getStreet());
		preparedStatement.setString(6, address.getHouseNumber());
		preparedStatement.setString(7, address.getApartmentNumber());
	}

	private Address getResultSet(ResultSet resultSet, Address address) throws SQLException {
		if (resultSet.next()) {
			address.setId(resultSet.getLong("id"));
			address.setUserId(resultSet.getLong("userId"));
			address.setCountry(resultSet.getString("country"));
			address.setRegion(resultSet.getString("region"));
			address.setCity(resultSet.getString("city"));
			address.setStreet(resultSet.getString("street"));
			address.setHouseNumber(resultSet.getString("houseNumber"));
			address.setApartmentNumber(resultSet.getString("apartmentNumber"));

			return address;
		} else
			return null;
	}

	@Override
	public boolean isConnectorUp() {
		return connector != null;
	}
}
