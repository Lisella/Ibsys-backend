package de.Ibsys.ibsys;

import javax.sql.DataSource;

import de.Ibsys.ibsys.Ordering.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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