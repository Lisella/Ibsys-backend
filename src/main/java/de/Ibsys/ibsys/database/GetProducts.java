package de.Ibsys.ibsys.database;

import com.zaxxer.hikari.HikariDataSource;
import de.Ibsys.ibsys.Ordering.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GetProducts {

    private static HikariDataSource dataSource = null;

    @Autowired
    public GetProducts(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM public.\"Product\"";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            Product product = new Product(
                    (Integer) row.get("ID"),
                    (String) row.get("name"),
                    (Integer) row.get("discountQuantity"),
                    (Integer) row.get("deliverytime"),
                    (Integer) row.get("variance"),
                    (Integer) row.get("product1Consumption"),
                    (Integer) row.get("product2Consumption"),
                    (Integer) row.get("product3Consumption"),
                    (Integer) row.get("stock"));
            products.add(product);
        }

        dataSource.close();
        return products;
    }
}
