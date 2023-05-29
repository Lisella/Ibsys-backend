package de.Ibsys.ibsys.database;

import de.Ibsys.ibsys.Ordering.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.ArrayList;

@Component
public class PutOrders {

    private final DataSource dataSource;

    @Autowired
    public PutOrders(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void putOrders(ArrayList<Order> orders) {
        clearOrdersTable();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO public.\"Order\" (order_id, product_id, quantity, daysAfterToday) VALUES (?, ?, ?, ?)";

        for (Order order : orders) {
            jdbcTemplate.update(sql, order.getId(), order.getProductId(), order.getQuantity(), order.getDaysAfterToday());
        }
    }

    private void clearOrdersTable() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "DELETE FROM public.\"Order\"";
        jdbcTemplate.update(sql);
    }
}
