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

        ArrayList<ProductionPlanEntity> planningList = new ArrayList<>();

        for (int i = 0; i < productionListJson.size(); i++) {
            Map<String, Object> productionItem = productionListJson.get(i);
            int product1Consumption = (int) productionItem.get("p1");
            int product2Consumption = (int) productionItem.get("p2");
            int product3Consumption = (int) productionItem.get("p3");

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

        System.out.println(("Bestellungen Berechnung gestartet"));
        ArrayList<NewOrder> orders = Calculations.createOrdersByProductionPlanning(planningList);

        System.out.println("Bestellungen Berechnung abgeschlossen:");
        System.out.println("----------------------");

        System.out.println(("Fertigungsaufträge Berechnung gestartet"));
        ArrayList<ProductionItem> productionItems = de.Ibsys.ibsys.Production.Calculations
                .createProductionByProductionPlanning(planningList);

        System.out.println("----------------------");
        System.out.println("Fertigungsaufträge Berechnung abgeschlossen:");
        System.out.println("----------------------");

        System.out.println(("Kapazitätsplanung Berechnung gestartet"));
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
