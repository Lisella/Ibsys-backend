package de.Ibsys.ibsys;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT COUNT(*) FROM public.\"Product\"";
        //Alternativ: "SELECT COUNT(*) FROM public.\"Order\"";
        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("Number of rows: " + rowCount);
    }
}