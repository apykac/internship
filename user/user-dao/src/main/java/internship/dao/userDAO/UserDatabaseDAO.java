package internship.dao.userDAO;

import internship.connectors.postgresConnector.IConnector;
import internship.models.userModel.User;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//FIXME Переписать запросы к базе, закрывать сессию (не ту, которая в универе, хотя, ту тоже лучше закрыть)
public class UserDatabaseDAO implements UserDAO {

    private IConnector connector;
    private Connection dbConnection;

    void setConnector(IConnector connector) {
        this.connector = connector;
        dbConnection = connector.getConnection();
    }

    @Override
    public User findUserById(Long id) {
        try {
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("birthday"),
                        resultSet.getLong("passportNumber"),
                        resultSet.getLong("income")
                );
            }
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User updateUser(Long id, User user) {
        try {
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE users " +
                    "SET name = ?, surname = ?, patronymic = ?, birthday = ?, \"passportNumber\" = ?, income = ? " +
                    "WHERE id = ? RETURNING id, name, surname, patronymic, birthday, \"passportNumber\", income");
            setStatement(user, preparedStatement);
            preparedStatement.setLong(7, id);

            User userFromResultSet = getResultSet(preparedStatement.executeQuery(), user);
            dbConnection.close();
            return userFromResultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        try {
            if (dbConnection == null)
                return null;

            PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO users" +
                    "(name, surname, patronymic, birthday, \"passportNumber\", income) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING id, name, surname, patronymic, birthday, \"passportNumber\", income");
            setStatement(user, preparedStatement);

            User userFromResultSet = getResultSet(preparedStatement.executeQuery(), user);
            dbConnection.close();
            return userFromResultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeUser(Long id) {
        try {
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

    private User getResultSet(ResultSet resultSet, User user) throws SQLException {
        if (resultSet.next()) {
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setPatronymic(resultSet.getString("patronymic"));
            user.setBirthday(resultSet.getString("birthday"));
            user.setPassportNumber(resultSet.getLong("passportNumber"));
            user.setIncome(resultSet.getLong("income"));

            return user;
        } else
            return null;
    }

    private void setStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getPatronymic());
        preparedStatement.setString(4, user.getBirthday());
        preparedStatement.setLong(5, user.getPassportNumber());
        preparedStatement.setLong(6, user.getIncome());
    }

    @Override
    public boolean isConnectorUp() {
        return connector != null;
    }
}
