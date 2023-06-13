package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.InputXml.Item;
import de.Ibsys.ibsys.database.ForecastsDB;
import de.Ibsys.ibsys.database.ProductsDB;
import de.Ibsys.ibsys.database.WaitingListForWorkstationsDB;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class XMLController {

    @PostMapping("/in")
    public String parseJson(@RequestBody Map<String, Object> requestBody) {

        List<Map<String, Object>> articles = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                .get("warehousestock"))
                .get("articles");
        HashMap<Integer, Integer> articlesMap = new HashMap<>();

        for (Map<String, Object> article : articles) {
            if (article != null) {
                Integer id = Integer.parseInt((String) article.get("id"));
                Integer amount = Integer.parseInt((String) article.get("amount"));
                articlesMap.put(id, amount);
            }
        }

        ProductsDB.updateProductStock(articlesMap);

        List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) requestBody
                .get("waitinglistworkstations");

        HashMap<Integer, Integer> workstations = new HashMap<>();

        for (Map<String, Object> workstation : waitingListWorkstations) {
            if (workstation != null) {
                Integer id = Integer.parseInt((String) workstation.get("id"));
                Integer timeNeed = Integer.parseInt((String) workstation.get("timeneed"));
                workstations.put(id, timeNeed);
            }
        }

        // gebe alle Werte der HashMap in der Console aus
        System.out.println("Warteliste der jeweiligen Arbeitsplätze:");
        for (Map.Entry<Integer, Integer> entry : workstations.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        WaitingListForWorkstationsDB.updateWaitingListForWorkstations(workstations);

        System.out.println(articlesMap);
        System.out.println(workstations);

        // SChreibe in die Console, dass die Forecast speichern beginnt
        System.out.println("Speichere Forecast");
        Map<String, Object> forecast = (Map<String, Object>) requestBody
                .get("forecast");

        // create a Hashmap to store the forecast
        // use the amount for p1 and set the index to 1
        HashMap<Integer, Integer> forecastMap = new HashMap<>();
        forecastMap.put(1, Integer.parseInt((String) forecast.get("p1")));

        // use the amount for p2 and set the index to 2
        forecastMap.put(2, Integer.parseInt((String) forecast.get("p2")));
        // use the amount for p3 and set the index to 3
        forecastMap.put(3, Integer.parseInt((String) forecast.get("p3")));

        // Gebe die Hashmap in der Console aus
        System.out.println(forecastMap);

        // Rufe die Datenbank Methode auf um die Forecasts zu speichern
        // ForecastsDB.updateForecasts(forecastMap);
        System.out.println("Forecast gespeichert");

        return "Ok";
    }

    @GetMapping("/forecast")
    public ResponseEntity<ArrayList<Item>> getForecast() {

        ArrayList<Item> forecast = ForecastsDB.getForecast();

        return ResponseEntity.ok(forecast);
    }
}
