package internship.connectors.postgresConnector;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Connector implements IConnector {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setDriverClassName(org.postgresql.Driver.class.getName());
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/vskDB");
        config.setUsername("postgres");
        config.setPassword("12345");
        config.setMinimumIdle(0);
        config.setMaximumPoolSize(10);
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() {
        log.info("getConnection() invoked");
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            log.error("Can't get connection from DataSource.");
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    Connector() {
    }
}
