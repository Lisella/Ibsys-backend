package de.Ibsys.ibsys.database;

import com.zaxxer.hikari.HikariDataSource;
import de.Ibsys.ibsys.InputXml.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForecastsDB {

    private static HikariDataSource dataSource = null;

    @Autowired
    public ForecastsDB(HikariDataSource dataSource) { this.dataSource = dataSource; }

    public static ArrayList<Item> getForecast() {
        ArrayList<Item> forecasts = new ArrayList<>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM public.\"Forecast\"";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            int id = (Integer) row.get("ID");
            int sellwish = (Integer) row.get("sellwish");

            Item forecast = new Item(id, sellwish);
            forecasts.add(forecast);
        }

        dataSource.close();
        return forecasts;
    }

    public static void updateForecasts(HashMap<Integer, Integer> forecast) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "UPDATE public.\"Forecast\" SET \"sellwish\" = ? WHERE \"ID\" = ?";
        forecast.forEach((id, newSellwish) -> {
            jdbcTemplate.update(sql, newSellwish, id);
        });
        dataSource.close();
    }
}
