package de.Ibsys.ibsys.Production;

import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import de.Ibsys.ibsys.database.ProductionProductsDB;

import java.util.ArrayList;

public class Calculations {

    public static ArrayList<ProductionItem> createProductionByProductionPlanning(
            ArrayList<ProductionPlanEntity> productionPlans) {

        ArrayList<ProductionItem> productionItems = new ArrayList<>();

        System.out.println("Für Produktionmengen zuerst alle Produkte die nicht bestellt werden können aus DB holen ");
        System.out.println("----------------------");

        ArrayList<ProductionProduct> products = ProductionProductsDB.getProductionProducts();

        System.out.println("Für Produktionsmengen den Produktverbrauch anhand der Produktionsmengen bestimmen");

        for (ProductionProduct product : products) {
            int quantity = product.product1Consumption * productionPlans.get(0).product1Consumption
                    + product.product2Consumption * productionPlans.get(0).product2Consumption
                    + product.product3Consumption * productionPlans.get(0).product3Consumption;
            productionItems.add(new ProductionItem(product.id, quantity));
        }

        // setzte die Sequenznummer für die Produktionsmengen
        for (int i = 0; i < productionItems.size(); i++) {
            productionItems.get(i).setSequenceNumber(i + 1);
            productionItems.get(i).id = i + 1;
        }

        System.out.println("----------------------");
        System.out.println("Folgende Produktionsmengen werden vorgeschlagen:");
        System.out.println("----------------------");
        for (ProductionItem productionItem : productionItems) {
            System.out.println("Article: " + productionItem.getArticle());
            System.out.println("Quantity: " + productionItem.getQuantity());
            System.out.println("Reihenfolge : " + productionItem.getSequenceNumer());
            System.out.println("----------------------");
        }

        System.out.println("Produktionsmengen Berechnung erfolgreich");

        return productionItems;
    }
}
