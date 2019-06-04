package internship.dao.userDAO;

import internship.connectors.postgresConnector.IConnector;
import internship.models.userModel.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabaseDAO implements UserDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private IConnector connector;

    void setConnector(IConnector connector) {
        this.connector = connector;
    }

    @Override
    public User findUserByPassport(Long passport) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement("SELECT * FROM users WHERE passport_number = ?")) {

            preparedStatement.setLong(1, passport);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSetToUser(resultSet, new User());
            } catch (SQLException e) {
                log.error("Can't get result set from database");
                log.error(e.getMessage());
                System.out.print(e.getMessage());
            }
        } catch (SQLException e) {
            log.error("Can't get user from database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    public User updateUser(Long passport, User user) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement("UPDATE users " +
                     "SET name = ?, surname = ?, patronymic = ?, birthday = ?, passport_number = ?, income = ? " +
                     "WHERE passport_number = ? RETURNING user_id, name, surname, patronymic, birthday, passport_number, income")) {

            setStatement(user, preparedStatement);
            preparedStatement.setLong(7, passport);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSetToUser(resultSet, user);
            } catch (SQLException e) {
                log.error("Can't get result set from database");
                log.error(e.getMessage());
                System.out.print(e.getMessage());
            }

        } catch (SQLException e) {
            log.error("Can't update user in database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    public User createUser(User user) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement("INSERT INTO users " +
                     "(name, surname, patronymic, birthday, passport_number, income) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING user_id, name, surname, patronymic, birthday, passport_number, income")) {

            setStatement(user, preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSetToUser(resultSet, user);
            } catch (SQLException e) {
                log.error("Can't get result set from database");
                log.error(e.getMessage());
                System.out.print(e.getMessage());
            }
        } catch (SQLException e) {
            log.error("Can't create user in database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
        return null;
    }

    @Override
    public void removeUser(Long passport) {
        try (Connection dbConnection = connector.getConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement("DELETE FROM users WHERE passport_number = ?")) {
            preparedStatement.setLong(1, passport);
            preparedStatement.execute();

        } catch (SQLException e) {
            log.error("Can't remove user from database");
            log.error(e.getMessage());
            System.out.print(e.getMessage());
        }
    }

    private User resultSetToUser(ResultSet resultSet, User user) throws SQLException {
        if (resultSet.next()) {
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setPatronymic(resultSet.getString("patronymic"));
            user.setBirthday(resultSet.getString("birthday"));
            user.setPassportNumber(resultSet.getLong("passport_number"));
            user.setIncome(resultSet.getLong("income"));
            return user;
        } else {
            return null;
        }
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
