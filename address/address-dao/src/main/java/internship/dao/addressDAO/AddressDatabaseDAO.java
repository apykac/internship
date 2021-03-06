package internship.dao.addressDAO;

import internship.connectors.postgresConnector.IConnector;
import internship.models.addressModel.Address;
import internship.models.addressModel.Addresses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddressDatabaseDAO implements AddressDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private IConnector connector;

    void setConnector(IConnector connector) {
        this.connector = connector;
    }

    @Override
    public Address findAddressById(Long id) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM addresses " +
                             "LEFT JOIN user_address ON user_address.address_id = addresses.address_id " +
                             "WHERE addresses.address_id = ?",
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                Address address = resultSetToAddress(resultSet, new Address());
                Set<Long> userPassports = new HashSet<>();
                while (resultSet.next()) {
                    Long userId = resultSet.getLong("user_passport_number");
                    if (!resultSet.wasNull())
                        userPassports.add(userId);
                }
                if (address != null) {
                    address.setUsers(userPassports);
                }

                return address;
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
    public Addresses findAddressesByUserPassport(Long passport) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM addresses " +
                             "LEFT JOIN user_address ON user_address.address_id = addresses.address_id " +
                             "WHERE user_address.user_passport_number = ?",
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            preparedStatement.setLong(1, passport);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                Addresses addresses = new Addresses();
                List<Address> addressList = new ArrayList<>();
                while (resultSet.next()) {
                    addressList.add(findAddressById(resultSet.getLong("address_id")));
                }
                addresses.setAddresses(addressList);

                return addresses;
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
             PreparedStatement addressStatement = dbConnection.prepareStatement("UPDATE addresses " +
                             "SET country = ?, region = ?, city = ?, street = ?, house_number = ?, apartment_number = ? " +
                             "WHERE address_id = ? RETURNING address_id, country, region, city, street, house_number, apartment_number",
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             PreparedStatement userStatement = dbConnection.prepareStatement("INSERT INTO user_address (user_passport_number, address_id) " +
                     "VALUES (?, ?) ON CONFLICT (user_passport_number, address_id) DO NOTHING RETURNING user_passport_number");
             PreparedStatement deleteDuplicate = dbConnection.prepareStatement("DELETE FROM user_address WHERE address_id = ? AND NOT user_passport_number = ANY(?)")) {

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
             PreparedStatement addressStatement = dbConnection.prepareStatement("INSERT INTO addresses " +
                             "(country, region, city, street, house_number, apartment_number) " +
                             "VALUES (?, ?, ?, ?, ?, ?) RETURNING address_id, country, region, city, street, house_number, apartment_number",
                     ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             PreparedStatement userStatement = dbConnection.prepareStatement("INSERT INTO user_address (user_passport_number, address_id) " +
                     "VALUES (?, ?) ON CONFLICT (user_passport_number, address_id) DO NOTHING RETURNING user_passport_number")) {

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
             PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM addresses WHERE address_id = ?")) {

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

    private Address resultSetToAddress(ResultSet resultSet, Address address) throws SQLException {
        if (resultSet.next()) {
            address.setId(resultSet.getLong("address_id"));
            address.setCountry(resultSet.getString("country"));
            address.setRegion(resultSet.getString("region"));
            address.setCity(resultSet.getString("city"));
            address.setStreet(resultSet.getString("street"));
            address.setHouseNumber(resultSet.getString("house_number"));
            address.setApartmentNumber(resultSet.getString("apartment_number"));

            resultSet.beforeFirst();
            return address;
        } else {
            return null;
        }
    }

    private void saveUserId(Address address, Long id, PreparedStatement userStatement) throws SQLException {
        userStatement.setLong(2, id);
        for (Long userID : address.getUsers()) {
            userStatement.setLong(1, userID);
            try (ResultSet resultSet = userStatement.executeQuery()) {
                if (resultSet.next())
                    address.getUsers().add(resultSet.getLong("user_passport_number"));
            }
        }

    }

    @Override
    public boolean isConnectorUp() {
        return connector != null;
    }
}
