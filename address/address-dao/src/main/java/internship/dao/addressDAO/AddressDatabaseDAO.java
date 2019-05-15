package internship.dao.addressDAO;

import internship.connectors.postgresConnector.Connector;
import internship.models.addressModel.Address;
import internship.models.addressModel.dto.AddressDTO;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDatabaseDAO implements AddressDAO {
    @Override
    public Address findAddressById(Long id) {
        try {
            Connector connector = new Connector();
            Connection dbConnection = connector.getConnection();
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM addresses WHERE id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getLong("id"));
                address.setUserId(resultSet.getLong("userId"));
                address.setCountry(resultSet.getString("country"));
                address.setRegion(resultSet.getString("region"));
                address.setCity(resultSet.getString("city"));
                address.setStreet(resultSet.getString("street"));
                address.setHouseNumber(resultSet.getString("houseNumber"));
                address.setApartmentNumber(resultSet.getString("apartmentNumber"));

                return address;
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        try {
            Connector connector = new Connector();
            Connection dbConnection = connector.getConnection();
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE addresses " +
                    "SET \"userId\" = ?, country = ?, region = ?, city = ?, street = ?, \"houseNumber\" = ?, \"apartmentNumber\" = ? " +
                    "WHERE id = ? RETURNING id, \"userId\", country, region, city, street, \"houseNumber\", \"apartmentNumber\"");
            preparedStatement.setLong(1, addressDTO.getUserId());
            preparedStatement.setString(2, addressDTO.getCountry());
            preparedStatement.setString(3, addressDTO.getRegion());
            preparedStatement.setString(4, addressDTO.getCity());
            preparedStatement.setString(5, addressDTO.getStreet());
            preparedStatement.setString(6, addressDTO.getHouseNumber());
            preparedStatement.setString(7, addressDTO.getApartmentNumber());
            preparedStatement.setLong(8, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getLong("id"));
                address.setUserId(resultSet.getLong("userId"));
                address.setCountry(resultSet.getString("country"));
                address.setRegion(resultSet.getString("region"));
                address.setCity(resultSet.getString("city"));
                address.setStreet(resultSet.getString("street"));
                address.setHouseNumber(resultSet.getString("houseNumber"));
                address.setApartmentNumber(resultSet.getString("apartmentNumber"));

                return address;
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        try {
            Connector connector = new Connector();
            Connection dbConnection = connector.getConnection();
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO public.addresses " +
                    "(\"userId\", country, region, city, street, \"houseNumber\", \"apartmentNumber\") " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id, \"userId\", country, region, city, street, \"houseNumber\", \"apartmentNumber\"");
            preparedStatement.setLong(1, addressDTO.getUserId());
            preparedStatement.setString(2, addressDTO.getCountry());
            preparedStatement.setString(3, addressDTO.getRegion());
            preparedStatement.setString(4, addressDTO.getCity());
            preparedStatement.setString(5, addressDTO.getStreet());
            preparedStatement.setString(6, addressDTO.getHouseNumber());
            preparedStatement.setString(7, addressDTO.getApartmentNumber());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getLong("id"));
                address.setUserId(resultSet.getLong("userId"));
                address.setCountry(resultSet.getString("country"));
                address.setRegion(resultSet.getString("region"));
                address.setCity(resultSet.getString("city"));
                address.setStreet(resultSet.getString("street"));
                address.setHouseNumber(resultSet.getString("houseNumber"));
                address.setApartmentNumber(resultSet.getString("apartmentNumber"));

                return address;
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeAddress(Long id) {
        try {
            Connector connector = new Connector();
            Connection dbConnection = connector.getConnection();
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
}
