package internship.connectors.postgresConnector;

import java.sql.Connection;

public interface IConnector {

    Connection getConnection();
}
