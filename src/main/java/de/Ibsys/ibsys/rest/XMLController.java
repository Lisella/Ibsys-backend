package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.service.InputService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class XMLController {

    @PostMapping("/in")
    public String parseJson(@RequestBody Map<String, Object> requestBody) {

        //Extracting Articles Id and Articles Amount
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
        InputService.updateArticles(articlesMap);
        System.out.println(articlesMap);

        //Extracting workinglistworkstations

        List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                .get("waitinglistworkstations"))
                .get("workplace");
        HashMap<Integer, Integer> workstations = new HashMap<>();


        for (Map<String, Object> workstation : waitingListWorkstations) {
            if (workstation != null) {
                Integer id = Integer.parseInt((String) workstation.get("id"));
                Integer timeNeed = Integer.parseInt((String) workstation.get("timeneed"));
                workstations.put(id, timeNeed);
            }
        }

        // Update articles and workstations
        InputService.updateArticles(articlesMap);
        //InputService.updateWorkstations(workstations);

        System.out.println(articlesMap);
        System.out.println(workstations);


        return "Ok";
    }

    @GetMapping("/input")
    public ResponseEntity<String> getResponse() {



        return ResponseEntity.ok("ok");
    }
}

