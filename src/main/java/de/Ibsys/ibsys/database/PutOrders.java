package de.Ibsys.ibsys.database;

import com.zaxxer.hikari.HikariDataSource;
import de.Ibsys.ibsys.Ordering.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class PutOrders {

    private final HikariDataSource dataSource;

    @Autowired
    public PutOrders(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void putOrders(ArrayList<Order> orders) {
        clearOrdersTable();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO public.\"Order\" (\"ID\", \"productID\", \"quantity\", \"days\") VALUES (?, ?, ?, ?)";

        for (Order order : orders) {
            jdbcTemplate.update(sql, order.getId(), order.getProductId(), order.getQuantity(), order.getDaysAfterToday());
        }
        dataSource.close();
    }

    private void clearOrdersTable() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "TRUNCATE TABLE public.\"Order\"";
        jdbcTemplate.update(sql);
        dataSource.close();
    }
}
