package internship.dao.addressDAO;

import internship.connectors.postgresConnector.IConnector;
import internship.models.addressModel.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AddressDatabaseDAO implements AddressDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private IConnector connector;

    void setConnector(IConnector connector) {
        this.connector = connector;
    }

    @Override
    public Address findAddressById(Long id) {
        try (Connection dbConnection = connector.getConnection()) {
            if (dbConnection == null)
                throw new ConnectException();

            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM addresses " +
                            "LEFT JOIN user_address ON user_address.address_id = addresses.address_id " +
                            "WHERE addresses.address_id = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                resultSet.beforeFirst();
                Set<Long> userIDs = new HashSet<>();
                while (resultSet.next()) {
                    Long userId = resultSet.getLong("user_id");
                    if (!resultSet.wasNull())
                        userIDs.add(userId);
                }

                resultSet.first();
                return new Address(
                        resultSet.getLong("address_id"),
                        userIDs,
                        resultSet.getString("country"),
                        resultSet.getString("region"),
                        resultSet.getString("city"),
                        resultSet.getString("street"),
                        resultSet.getString("house_number"),
                        resultSet.getString("apartment_number")
                );
            }
        } catch (SQLException | ConnectException e) {
            log.error("Can't get address from database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    public Address updateAddress(Long id, Address address) {
        try (Connection dbConnection = connector.getConnection()) {
            if (dbConnection == null)
                throw new ConnectException();

            PreparedStatement addressStatement = dbConnection.prepareStatement("UPDATE addresses " +
                            "SET country = ?, region = ?, city = ?, street = ?, house_number = ?, apartment_number = ? " +
                            "WHERE address_id = ? RETURNING address_id, country, region, city, street, house_number, apartment_number",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            setStatement(address, addressStatement);
            addressStatement.setLong(7, id);

            ResultSet addressResultSet = addressStatement.executeQuery();
            resultSetToAddress(addressResultSet, address);

            PreparedStatement userStatement = dbConnection.prepareStatement("INSERT INTO user_address (user_id, address_id) " +
                    "VALUES (?, ?) ON CONFLICT (user_id, address_id) DO NOTHING RETURNING user_id");
            saveUserId(address, id, userStatement);

            PreparedStatement deleteDuplicate = dbConnection.prepareStatement("DELETE FROM user_address WHERE address_id = ? AND NOT user_id = ANY(?)");
            deleteDuplicate.setLong(1, id);
            deleteDuplicate.setArray(2, dbConnection.createArrayOf("bigint", address.getUserId().toArray()));
            deleteDuplicate.execute();

            return address;

        } catch (SQLException | ConnectException e) {
            log.error("Can't update address in database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    public Address createAddress(Address address) {
        try (Connection dbConnection = connector.getConnection()) {
            if (dbConnection == null)
                throw new ConnectException();

            PreparedStatement addressStatement = dbConnection.prepareStatement("INSERT INTO addresses " +
                            "(country, region, city, street, house_number, apartment_number) " +
                            "VALUES (?, ?, ?, ?, ?, ?) RETURNING address_id, country, region, city, street, house_number, apartment_number",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            setStatement(address, addressStatement);

            ResultSet addressResultSet = addressStatement.executeQuery();
            resultSetToAddress(addressResultSet, address);

            PreparedStatement userStatement = dbConnection.prepareStatement("INSERT INTO user_address " +
                    "(user_id, address_id) VALUES (?, ?) RETURNING user_id");
            if (addressResultSet.next())
                saveUserId(address, addressResultSet.getLong("address_id"), userStatement);

            return address;

        } catch (SQLException | ConnectException e) {
            log.error("Can't create address in database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());

        }
        return null;
    }

    @Override
    public void removeAddress(Long id) {
        try (Connection dbConnection = connector.getConnection()) {
            if (dbConnection == null)
                throw new ConnectException();

            PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM addresses WHERE address_id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException | ConnectException e) {
            log.error("Can't remove address from database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
    }

    private void setStatement(Address address, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, address.getCountry());
        preparedStatement.setString(2, address.getRegion());
        preparedStatement.setString(3, address.getCity());
        preparedStatement.setString(4, address.getStreet());
        preparedStatement.setString(5, address.getHouseNumber());
        preparedStatement.setString(6, address.getApartmentNumber());
    }

    private void resultSetToAddress(ResultSet resultSet, Address address) throws SQLException {
        if (resultSet.next()) {
            address.setId(resultSet.getLong("address_id"));
            address.setCountry(resultSet.getString("country"));
            address.setRegion(resultSet.getString("region"));
            address.setCity(resultSet.getString("city"));
            address.setStreet(resultSet.getString("street"));
            address.setHouseNumber(resultSet.getString("house_number"));
            address.setApartmentNumber(resultSet.getString("apartment_number"));

            resultSet.beforeFirst();
        }
    }

    private void saveUserId(Address address, Long id, PreparedStatement userStatement) throws SQLException {
        userStatement.setLong(2, id);
        ResultSet bindingResult;
        for (Long userID : address.getUserId()) {
            userStatement.setLong(1, userID);
            bindingResult = userStatement.executeQuery();
            if (bindingResult.next())
                address.getUserId().add(bindingResult.getLong("user_id"));
        }
    }

    @Override
    public boolean isConnectorUp() {
        return connector != null;
    }
}
