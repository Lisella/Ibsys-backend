package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.Production.Calculations;
import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import de.Ibsys.ibsys.Production.ProductionItem;
import de.Ibsys.ibsys.Production.ReserveStockProduct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlanningController {

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/productionorders")
    public ArrayList<ProductionItem> processPlanning(@RequestBody Map<String, Object> requestBody) {
        List<Map<String, Object>> productionListJson = (List<Map<String, Object>>) requestBody.get("production");
        List<Map<String, Object>> reserveStockListJson = (List<Map<String, Object>>) requestBody.get("products");

        ArrayList<ReserveStockProduct> productionList = createProductionList(reserveStockListJson);
        ArrayList<ProductionPlanEntity> planningList = createPlanningList(productionListJson);

        printReserveStocks(productionList);
        printPlanningList(planningList);

        ArrayList<ProductionItem> productionItems = Calculations.createProductionByProductionPlanning(planningList, productionList);

        printCalculationResult(productionItems);

        return productionItems;
    }

    private ArrayList<ReserveStockProduct> createProductionList(List<Map<String, Object>> reserveStockListJson) {
        ArrayList<ReserveStockProduct> productionList = new ArrayList<>();

        for (Map<String, Object> reserveStockItem : reserveStockListJson) {
            int productId = (int) reserveStockItem.get("productId");
            int reserveStock = (int) reserveStockItem.get("reserveStock");

            ReserveStockProduct reserveStockProduct = new ReserveStockProduct(productId, reserveStock);
            productionList.add(reserveStockProduct);
        }

        return productionList;
    }

    private ArrayList<ProductionPlanEntity> createPlanningList(List<Map<String, Object>> productionListJson) {
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

        return planningList;
    }

    private void printReserveStocks(ArrayList<ReserveStockProduct> productionList) {
        System.out.println("Sicherheitsbestände:");
        for (ReserveStockProduct reserveStockProduct : productionList) {
            System.out.println("ProductId: " + reserveStockProduct.getProductId() + " Sicherheitsbestand: "
                    + reserveStockProduct.getReserveStock());
        }
        System.out.println("----------------------");
    }

    private void printPlanningList(ArrayList<ProductionPlanEntity> planningList) {
        System.out.println("Produktionsplanung:");
        for (ProductionPlanEntity planEntity : planningList) {
            System.out.println("Periode: " + planEntity.getPeriode());
            System.out.println("Product1Consumption: " + planEntity.product1Consumption);
            System.out.println("Product2Consumption: " + planEntity.product2Consumption);
            System.out.println("Product3Consumption: " + planEntity.product3Consumption);
            System.out.println("----------------------");
        }
    }

    private void printCalculationResult(ArrayList<ProductionItem> productionItems) {
        System.out.println("Fertigungsaufträge Berechnung gestartet");
        System.out.println("----------------------");
        System.out.println("Fertigungsaufträge Berechnung abgeschlossen:");
        System.out.println("----------------------");

        System.out.println("Fertigungsaufträge:");
        for (ProductionItem item : productionItems) {
            System.out.println("Produkt: " + item.getId() + ", Menge: " + item.getQuantity());
        }
    }

}
