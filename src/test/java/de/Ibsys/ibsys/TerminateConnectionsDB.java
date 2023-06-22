package de.Ibsys.ibsys;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Schließt alle idle connections
 */
@SpringBootTest
public class TerminateConnectionsDB {

    @Autowired
    private HikariDataSource dataSource;

    @Test
    public void terminateConnections() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT pg_terminate_backend(pg_stat_activity.pid)\n" +
                "FROM pg_stat_activity\n" +
                "WHERE pg_stat_activity.datname = 'mzmjhokr'\n" +
                "  AND pid <> pg_backend_pid()";
        jdbcTemplate.queryForList(sql);
        dataSource.close();
    }
}

