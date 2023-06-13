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

        List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                .get("waitinglistworkstation"))
                .get("workplace");
        HashMap<Integer, Integer> workstations = new HashMap<>();


        for (Map<String, Object> workstation : waitingListWorkstations) {
            if (workstation != null) {
                Integer id = Integer.parseInt((String) workstation.get("id"));
                Integer timeNeed = Integer.parseInt((String) workstation.get("timeneed"));
                workstations.put(id, timeNeed);
            }
        }

        WaitingListForWorkstationsDB.updateWaitingListForWorkstations(workstations);

        System.out.println(articlesMap);
        System.out.println(workstations);

        //List<Map<String, Object>> forecast = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                //.get("waitinglistworkstation"))
                //.get("workplace");
       // HashMap<Integer, Integer> workstations = new HashMap<>();



        return "Ok";
    }

    @GetMapping("/forecast")
    public ResponseEntity<ArrayList<Item>> getForecast() {

        ArrayList<Item> forecast = ForecastsDB.getForecast();

        return ResponseEntity.ok(forecast);



    }




    @GetMapping("/input")
    public ResponseEntity<String> getResponse() {



        return ResponseEntity.ok("ok");
    }
}

