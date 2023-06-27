package de.Ibsys.ibsys.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zaxxer.hikari.HikariDataSource;
import de.Ibsys.ibsys.Ordering.Order;
import de.Ibsys.ibsys.Production.WaitingListProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class WaitingListProductsDB {

    private static HikariDataSource dataSource = null;

    @Autowired
    public WaitingListProductsDB(HikariDataSource dataSource) { this.dataSource = dataSource; }

    // Wie viele von welchem Produkt. Maschine egal.
    public static ArrayList<WaitingListProduct> GetWaitingListProductsFromDB() {
        ArrayList<WaitingListProduct> waitingListProducts = new ArrayList<>();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM public.\"ProductWaitlist\"";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            WaitingListProduct waitingListProduct = new WaitingListProduct(
                    (Integer) row.get("ID"),
                    (Integer) row.get("quantity"));
            waitingListProducts.add(waitingListProduct);
        }

        // dataSource.close();
        return waitingListProducts;
    }

    public static void putWaitingListProducts(ArrayList<WaitingListProduct> waitingListProducts) {
        clearWaitingListProductTable();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "INSERT INTO public.\"ProductWaitlist\" (\"ID\", \"quantity\") VALUES (?, ?)";

        for (WaitingListProduct waitingListProduct : waitingListProducts) {
            jdbcTemplate.update(sql, waitingListProduct.getProductId(), waitingListProduct.getQuantity());
        }
        // dataSource.close();
    }

    public static void updateWaitingListProducts(ArrayList<WaitingListProduct> waitingListProducts) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "UPDATE public.\"ProductWaitlist\" SET \"quantity\" = ? WHERE \"ID\" = ?";
        for (WaitingListProduct waitingListProduct : waitingListProducts) {
            jdbcTemplate.update(sql, waitingListProduct.getQuantity(), waitingListProduct.getProductId());
        }
        // dataSource.close();
    }

    private static void clearWaitingListProductTable() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "TRUNCATE TABLE public.\"ProductWaitlist\"";
        jdbcTemplate.update(sql);
        // dataSource.close();
    }
}
