package de.Ibsys.ibsys.database;

import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.Production.ProductionProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GetProductionProducts {

    private static DataSource dataSource = null;

    @Autowired
    public GetProductionProducts(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ArrayList<ProductionProduct> getProductionProducts() {
        ArrayList<ProductionProduct> productionProducts = new ArrayList<>();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM public.\"ProductionProduct\"";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            ProductionProduct productionProduct = new ProductionProduct(
                    (Integer) row.get("ID"),
                    (String) row.get("name"),
                    (Integer) row.get("product1Consumption"),
                    (Integer) row.get("product2Consumption"),
                    (Integer) row.get("product3Consumption"));
            productionProducts.add(productionProduct);
        }

        return productionProducts;
    }
}
