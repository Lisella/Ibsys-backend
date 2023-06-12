package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.Ordering.Calculations;
import de.Ibsys.ibsys.Ordering.NewOrder;
import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import de.Ibsys.ibsys.Production.ProductionItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlanningController {

    @CrossOrigin(origins = "http://localhost:5173")

    @PostMapping("/planning")
    public ResponseEntity<Map<String, Object>> processPlanning(@RequestBody Map<String, Object> requestBody) {
        List<Map<String, Object>> productionListJson = (List<Map<String, Object>>) requestBody.get("production");
        Map<String, Map<String, Object>> directListJson = (Map<String, Map<String, Object>>) requestBody.get("direct");
        boolean splitting = (boolean) requestBody.get("splitting");

        ArrayList<ProductionPlanEntity> planningList = new ArrayList<>();

        for (int i = 0; i < productionListJson.size(); i++) {
            Map<String, Object> productionItem = productionListJson.get(i);
            int product1Consumption = (int) productionItem.get("p1");
            int product2Consumption = (int) productionItem.get("p2");
            int product3Consumption = (int) productionItem.get("p3");

            // Add the quantity of direct p1 to production p1 if present in the JSON
            if (i == 0 && directListJson.containsKey("p1")) {
                int directP1Quantity = (int) directListJson.get("p1").get("quantity");
                product1Consumption += directP1Quantity;
            }

            // Add the quantity of direct p2 to production p2 if present in the JSON
            if (i == 0 && directListJson.containsKey("p2")) {
                int directP2Quantity = (int) directListJson.get("p2").get("quantity");
                product2Consumption += directP2Quantity;
            }

            // Add the quantity of direct p3 to production p3 if present in the JSON
            if (i == 0 && directListJson.containsKey("p3")) {
                int directP3Quantity = (int) directListJson.get("p3").get("quantity");
                product3Consumption += directP3Quantity;
            }

            ProductionPlanEntity planEntity = new ProductionPlanEntity(i + 1, product1Consumption, product2Consumption,
                    product3Consumption);
            planningList.add(planEntity);
        }

        // gebe die Liste der Produktionsplanung aus
        System.out.println("Produktionsplanung:");
        for (ProductionPlanEntity planEntity : planningList) {
            System.out.println("Periode: " + planEntity.getPeriode());
            System.out.println("Product1Consumption: " + planEntity.product1Consumption);
            System.out.println("Product2Consumption: " + planEntity.product2Consumption);
            System.out.println("Product3Consumption: " + planEntity.product3Consumption);
            System.out.println("----------------------");
        }

        System.out.println("Aufträge sollen gesplittet werden: " + splitting);

        System.out.println(("Bestellungen Berechnung gestartet"));
        ArrayList<NewOrder> orders = Calculations.createOrdersByProductionPlanning(planningList);

        ArrayList<ProductionItem> productionItems = de.Ibsys.ibsys.Production.Calculations
                .createProductionByProductionPlanning(planningList, splitting);

        System.out.println(("Überstunden Berechnung gestartet"));
        ArrayList<de.Ibsys.ibsys.WorkingTimes.WorkingTime> workingTimes = de.Ibsys.ibsys.WorkingTimes.Calculations
                .CalculateWorkingtimesByProductionList(productionItems);

        // Create a map to hold the response data
        Map<String, Object> response = new HashMap<>();
        response.put("orderlist", orders);
        response.put("productionlist", productionItems);
        response.put("workingtimelist", workingTimes);

        // Return the response map with the appropriate status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
