package de.Ibsys.ibsys.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.Ibsys.ibsys.Ordering.Order;
import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.WorkingTimes.Workplace;
import de.Ibsys.ibsys.WorkingTimes.WorkplaceProductMerge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class GetWorkplaces {

    private static DataSource dataSource = null;

    @Autowired
    public GetWorkplaces(DataSource dataSource) { this.dataSource = dataSource; }

    public static ArrayList<Workplace> getWorkplaces() {
        ArrayList<Workplace> workplaces = new ArrayList<>();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM public.\"Workplace\"";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : rows) {
            int id = (Integer) row.get("ID");
            int duration = (Integer) row.get("duration");
            String json = (String) row.get("durationsProducts");

            ArrayList<WorkplaceProductMerge> durationsProducts = convertJsonToWorkplaceProductMergeList(json);

            Workplace workplace = new Workplace(id, duration, durationsProducts);
            workplaces.add(workplace);
        }

        return workplaces;
    }

    private static ArrayList<WorkplaceProductMerge> convertJsonToWorkplaceProductMergeList(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<ArrayList<WorkplaceProductMerge>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
