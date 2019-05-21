package internship.connectors.postgresConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector implements IConnector {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public Connection getConnection() {
		log.info("getConnection() invoked");

		String url = "jdbc:postgresql://localhost:5432/vskDB";
		String name = "postgres";
		String password = "root";
		Connection dbConnection;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.error("Can't register JDBC driver.");
			log.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(url, name, password);
			return dbConnection;
		} catch (SQLException e) {
			log.error("Can't get connection from DriverManager.");
			log.error(e.getMessage());
			System.out.println(e.getMessage());
		}
		return null;
	}
}
