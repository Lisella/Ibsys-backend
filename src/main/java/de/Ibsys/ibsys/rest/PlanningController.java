package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.InputXml.WorkingTime;
import de.Ibsys.ibsys.Ordering.Calculations;
import de.Ibsys.ibsys.Ordering.NewOrder;
import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import de.Ibsys.ibsys.Production.ProductionItem;
import de.Ibsys.ibsys.WorkingTimes.Workplace;
import org.glassfish.jersey.internal.guava.Ordering;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlanningController {
    @PostMapping("/planning")
    public ResponseEntity<Map<String, Object>> processPlanning(@RequestBody Map<String, Object> requestBody) {
        List<Map<String, Object>> productionListJson = (List<Map<String, Object>>) requestBody.get("production");
        boolean splitting = (boolean) requestBody.get("splitting");

        ArrayList<ProductionPlanEntity> planningList = new ArrayList<>();

        for (Map<String, Object> productionItem : productionListJson) {
            int periode = (int) productionItem.get("periode");
            int product1Consumption = (int) productionItem.get("product1Consumption");
            int product2Consumption = (int) productionItem.get("product2Consumption");
            int product3Consumption = (int) productionItem.get("product3Consumption");

            ProductionPlanEntity planEntity = new ProductionPlanEntity(periode, product1Consumption,
                    product2Consumption, product3Consumption);
            planningList.add(planEntity);
        }

        System.out.println("Aufträge sollen gesplittet werden: " + splitting);

        // calls a methode, that uses the ArrayList<ProductionPlanEntity> planningList
        // and start the ordering calcs
        System.out.println(("Bestellungen Berechnung gestartet"));
        ArrayList<NewOrder> orders = Calculations.createOrdersByProductionPlanning(planningList);

        ArrayList<ProductionItem> productionItems = de.Ibsys.ibsys.Production.Calculations
                .createProductionByProductionPlanning(planningList, splitting);

        System.out.println(("Überstunden Berechnung gestartet"));
        ArrayList<de.Ibsys.ibsys.WorkingTimes.WorkingTime> workingTimes = de.Ibsys.ibsys.WorkingTimes.Calculations
                .CalculateWorkingtimesByProductionList(productionItems);
        /*
         * // Create the production list
         * List<Map<String, String>> productionList = new ArrayList<>();
         * Map<String, String> productionItem1 = new HashMap<>();
         * productionItem1.put("article", "4");
         * productionItem1.put("quantity", "150");
         * productionList.add(productionItem1);
         * 
         * Map<String, String> productionItem2 = new HashMap<>();
         * productionItem2.put("article", "5");
         * productionItem2.put("quantity", "300");
         * productionList.add(productionItem2);
         * 
         * // Create the working time list
         * List<Map<String, String>> workingTimeList = new ArrayList<>();
         * Map<String, String> workingTimeItem1 = new HashMap<>();
         * workingTimeItem1.put("station", "1");
         * workingTimeItem1.put("shift", "2");
         * workingTimeItem1.put("overtime", "0");
         * workingTimeList.add(workingTimeItem1);
         * 
         * Map<String, String> workingTimeItem2 = new HashMap<>();
         * workingTimeItem2.put("station", "2");
         * workingTimeItem2.put("shift", "1");
         * workingTimeItem2.put("overtime", "2");
         * workingTimeList.add(workingTimeItem2);
         */
        // Create a map to hold the response data
        Map<String, Object> response = new HashMap<>();
        response.put("orderlist", orders);
        response.put("productionlist", productionItems);
        response.put("workingtimelist", workingTimes);

        // Return the response map with the appropriate status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
