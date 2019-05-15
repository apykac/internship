package internship.dao.userDAO;

import internship.connectors.postgresConnector.Connector;
import internship.models.userModel.User;
import internship.models.userModel.dto.UserDTO;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabaseDAO implements UserDAO {
    @Override
    public User findUserById(Long id) {
        try {
            Connector connector = new Connector();
            Connection dbConnection = connector.getConnection();
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPatronymic(resultSet.getString("patronymic"));
                user.setBirthday(resultSet.getString("birthday"));
                user.setPassportNumber(resultSet.getLong("passportNumber"));
                user.setIncome(resultSet.getLong("income"));

                return user;
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        try {
            Connector connector = new Connector();
            Connection dbConnection = connector.getConnection();
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE users " +
                    "SET name = ?, surname = ?, patronymic = ?, birthday = ?, \"passportNumber\" = ?, income = ? " +
                    "WHERE id = ? RETURNING id, name, surname, patronymic, birthday, \"passportNumber\", income");
            preparedStatement.setString(1, userDTO.getName());
            preparedStatement.setString(2, userDTO.getSurname());
            preparedStatement.setString(3, userDTO.getPatronymic());
            preparedStatement.setString(4, userDTO.getBirthday());
            preparedStatement.setLong(5, userDTO.getPassportNumber());
            preparedStatement.setLong(6, userDTO.getIncome());
            preparedStatement.setLong(7, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPatronymic(resultSet.getString("patronymic"));
                user.setBirthday(resultSet.getString("birthday"));
                user.setPassportNumber(resultSet.getLong("passportNumber"));
                user.setIncome(resultSet.getLong("income"));

                return user;
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        try {
            Connector connector = new Connector();
            Connection dbConnection = connector.getConnection();
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO users" +
                    "(name, surname, patronymic, birthday, \"passportNumber\", income) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING id, name, surname, patronymic, birthday, \"passportNumber\", income");
            preparedStatement.setString(1, userDTO.getName());
            preparedStatement.setString(2, userDTO.getSurname());
            preparedStatement.setString(3, userDTO.getPatronymic());
            preparedStatement.setString(4, userDTO.getBirthday());
            preparedStatement.setLong(5, userDTO.getPassportNumber());
            preparedStatement.setLong(6, userDTO.getIncome());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPatronymic(resultSet.getString("patronymic"));
                user.setBirthday(resultSet.getString("birthday"));
                user.setPassportNumber(resultSet.getLong("passportNumber"));
                user.setIncome(resultSet.getLong("income"));

                return user;
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeUser(Long id) {
        try {
            Connector connector = new Connector();
            Connection dbConnection = connector.getConnection();
            if (dbConnection == null)
                throw new ConnectException();

            PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            dbConnection.close();
        } catch (SQLException | ConnectException e) {
            System.out.println(e.getMessage());
        }
    }
}
