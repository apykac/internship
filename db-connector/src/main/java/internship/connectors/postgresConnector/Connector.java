package internship.connectors.postgresConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector implements IConnector {

    public Connection getConnection() {

        String url = "jdbc:postgresql://localhost:5432/vskDB";
        String name = "postgres";
        String password = "12345";
        Connection dbConnection;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(url, name, password);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
