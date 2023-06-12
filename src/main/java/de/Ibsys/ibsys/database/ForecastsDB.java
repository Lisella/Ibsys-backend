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
            int amount = (Integer) row.get("amount");

            Item forecast = new Item(id, amount);
            forecasts.add(forecast);
        }

        //dataSource.close();
        return forecasts;
    }

    public static void updateForecasts(HashMap<Integer, Integer> forecast) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "UPDATE public.\"Forecast\" SET \"amount\" = ? WHERE \"ID\" = ?";
        forecast.forEach((id, newAmount) -> {
            jdbcTemplate.update(sql, newAmount, id);
        });
        //dataSource.close();
    }
}
