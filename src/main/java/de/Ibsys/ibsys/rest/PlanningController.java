package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.Ordering.NewOrder;
import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
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
    public ResponseEntity<Map<String, Object>> processPlanning(@RequestBody ArrayList<ProductionPlanEntity> planningList) {
        // Here you can process the received list
        // For example:
        for (ProductionPlanEntity planEntity : planningList) {
            System.out.println("Periode: " + planEntity.periode);
            System.out.println("Product1 Consumption: " + planEntity.product1Consumption);
            System.out.println("Product2 Consumption: " + planEntity.product2Consumption);
            System.out.println("Product3 Consumption: " + planEntity.product3Consumption);
        }

        NewOrder order = new NewOrder(1, 200, 5);

        ArrayList<NewOrder> orderList = new ArrayList<>();
        orderList.add(order);
        orderList.add(new NewOrder(2, 200, 3));

        // Create the production list
        List<Map<String, String>> productionList = new ArrayList<>();
        Map<String, String> productionItem1 = new HashMap<>();
        productionItem1.put("article", "4");
        productionItem1.put("quantity", "150");
        productionList.add(productionItem1);

        Map<String, String> productionItem2 = new HashMap<>();
        productionItem2.put("article", "5");
        productionItem2.put("quantity", "300");
        productionList.add(productionItem2);

        // Create the working time list
        List<Map<String, String>> workingTimeList = new ArrayList<>();
        Map<String, String> workingTimeItem1 = new HashMap<>();
        workingTimeItem1.put("station", "1");
        workingTimeItem1.put("shift", "2");
        workingTimeItem1.put("overtime", "0");
        workingTimeList.add(workingTimeItem1);

        Map<String, String> workingTimeItem2 = new HashMap<>();
        workingTimeItem2.put("station", "2");
        workingTimeItem2.put("shift", "1");
        workingTimeItem2.put("overtime", "2");
        workingTimeList.add(workingTimeItem2);

        // Create a map to hold the response data
        Map<String, Object> response = new HashMap<>();
        response.put("orderlist", orderList);
        response.put("productionlist", productionList);
        response.put("workingtimelist", workingTimeList);

        // Return the response map with the appropriate status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
