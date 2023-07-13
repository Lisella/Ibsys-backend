package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.Ordering.Calculations;
import de.Ibsys.ibsys.Ordering.NewOrder;
import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import de.Ibsys.ibsys.Production.ReserveStockProduct;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrdersController {

    @CrossOrigin(origins = "http://localhost:5173")

    @PostMapping("/orders")
    public ArrayList<NewOrder> processPlanning(@RequestBody Map<String, Object> requestBody) {
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

        for (NewOrder order : orders) {
            System.out.println("Artikel: " + order.getArticle());
            System.out.println("Menge: " + order.getQuantity());
            System.out.println("Modus: " + order.getModus());
            System.out.println("Infos: " + order.getOrderInfos());
            System.out.println("----------------------");
        }

        return orders;
    }
}
