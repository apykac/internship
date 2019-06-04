package internship.connectors.postgresConnector;

import java.sql.Connection;

public interface IConnector {
    /**
     * Выдает connection к базе данных
     *
     * @return connection, если удалось подключиться к базе данных, иначе null
     */
    Connection getConnection();
}
