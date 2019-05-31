package internship.dao.addressDAO;

import internship.connectors.postgresConnector.IConnector;
import internship.models.addressModel.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AddressDatabaseDAO implements AddressDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private IConnector connector;

    private final String CREATE_ADDRESS = "INSERT INTO addresses " +
            "(country, region, city, street, house_number, apartment_number) " +
            "VALUES (?, ?, ?, ?, ?, ?) RETURNING address_id, country, region, city, street, house_number, apartment_number";

    private final String BIND_USER = "INSERT INTO user_address (user_id, address_id) " +
            "VALUES (?, ?) ON CONFLICT (user_id, address_id) DO NOTHING RETURNING user_id";

    private final String GET_ADDRESS = "SELECT * FROM addresses " +
            "LEFT JOIN user_address ON user_address.address_id = addresses.address_id " +
            "WHERE addresses.address_id = ?";

    private final String UPDATE_ADDRESS = "UPDATE addresses " +
            "SET country = ?, region = ?, city = ?, street = ?, house_number = ?, apartment_number = ? " +
            "WHERE address_id = ? RETURNING address_id, country, region, city, street, house_number, apartment_number";

    private final String DELETE_USER_WITHOUT_ADDRESS = "DELETE FROM user_address WHERE address_id = ? AND NOT user_id = ANY(?)";

    private final String DELETE_ADDRESS = "DELETE FROM addresses WHERE address_id = ?";

    void setConnector(IConnector connector) {
        this.connector = connector;
    }

    @Override
    public Address findAddressById(Long id) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_ADDRESS,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            } catch (SQLException e) {
                log.error("Can't get result set from database");
                log.error(e.getMessage());
                System.out.print(e.getMessage());
            }
        } catch (SQLException e) {
            log.error("Can't get address from database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    public Address updateAddress(Long id, Address address) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement addressStatement = dbConnection.prepareStatement(UPDATE_ADDRESS,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             PreparedStatement userStatement = dbConnection.prepareStatement(BIND_USER);
             PreparedStatement deleteDuplicate = dbConnection.prepareStatement(DELETE_USER_WITHOUT_ADDRESS)) {

            setStatement(address, addressStatement);
            addressStatement.setLong(7, id);

            try (ResultSet resultSet = addressStatement.executeQuery()) {
                resultSetToAddress(resultSet, address);
                saveUserId(address, id, userStatement);

                deleteDuplicate.setLong(1, id);
                deleteDuplicate.setArray(2, dbConnection.createArrayOf("bigint", address.getUsers().toArray()));
                deleteDuplicate.execute();

                return address;
            } catch (SQLException e) {
                log.error("Can't get result set from database");
                log.error(e.getMessage());
                System.out.print(e.getMessage());
            }

        } catch (SQLException e) {
            log.error("Can't update address in database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    public Address createAddress(Address address) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement addressStatement = dbConnection.prepareStatement(CREATE_ADDRESS,
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             PreparedStatement userStatement = dbConnection.prepareStatement(BIND_USER)) {

            setStatement(address, addressStatement);

            try (ResultSet resultSet = addressStatement.executeQuery()) {
                resultSetToAddress(resultSet, address);
                if (resultSet.next())
                    saveUserId(address, resultSet.getLong("address_id"), userStatement);

                return address;
            } catch (SQLException e) {
                log.error("Can't get result set from database");
                log.error(e.getMessage());
                System.out.print(e.getMessage());
            }

        } catch (SQLException e) {
            log.error("Can't create address in database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());

        }
        return null;
    }

    @Override
    public void removeAddress(Long id) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement(DELETE_ADDRESS)) {

            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
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
        for (Long userID : address.getUsers()) {
            userStatement.setLong(1, userID);
            try (ResultSet resultSet = userStatement.executeQuery()) {
                if (resultSet.next())
                    address.getUsers().add(resultSet.getLong("user_id"));
            }
        }

    }

    @Override
    public boolean isConnectorUp() {
        return connector != null;
    }
}
