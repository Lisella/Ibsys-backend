package de.Ibsys.ibsys.database;

import de.Ibsys.ibsys.Ordering.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GetOrders {

    private static DataSource dataSource = null;

    @Autowired
    public GetOrders(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM public.\"Order\"";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            Order order = new Order(
                    (Integer) row.get("ID"),
                    (Integer) row.get("productID"),
                    (Integer) row.get("quantity"),
                    (Integer) row.get("days"));
            orders.add(order);
        }

        return orders;
    }
}
