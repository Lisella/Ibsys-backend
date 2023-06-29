package de.Ibsys.ibsys.Production;

import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import de.Ibsys.ibsys.database.ProductionProductsDB;
import de.Ibsys.ibsys.database.WaitingListProductsDB;

import java.util.ArrayList;

public class Calculations {

    public static ArrayList<ProductionItem> createProductionByProductionPlanning(
            ArrayList<ProductionPlanEntity> productionPlan, ArrayList<ReserveStockProduct> reserveStockProducts) {

        ArrayList<ProductionItem> productionItems = new ArrayList<>();

        System.out.println("Für Produktionmengen zuerst alle Produkte die nicht bestellt werden können aus DB holen ");
        System.out.println("----------------------");

        ArrayList<ProductionProduct> products = ProductionProductsDB.getProductionProducts();

        // gebe alle Produkte aus, mit dem aktuellen Lagerbestand
        for (ProductionProduct product : products) {
            System.out.println("Article: " + product.name + " Stock: " + product.stock);
            System.out.println("----------------------");
        }

        System.out.println("Für Produktionsmengen den Produktverbrauch anhand der Produktionsmengen bestimmen");

        for (ProductionProduct product : products) {
            System.out.println("----------------------");
            System.out.println(
                    "Artikel: " + product.name + " Verbrauch (p1, p2, p3): " + product.product1Consumption + " "
                            + product.product2Consumption + " " + product.product3Consumption);
            int quantity = product.product1Consumption * productionPlan.get(0).product1Consumption
                    + product.product2Consumption * productionPlan.get(0).product2Consumption
                    + product.product3Consumption * productionPlan.get(0).product3Consumption;
            System.out.println("Artikel: " + product.id + " Quantity: " + quantity);
            quantity = quantity + ReserveStockProduct.GetReserveStock(product.id, reserveStockProducts);
            System.out.println("Addition von Sicherheitsbestand für Artikel: " + product.id + " Menge: " + quantity
                    + " Sicherheitsbestand: " + ReserveStockProduct.GetReserveStock(product.id, reserveStockProducts));
            quantity = quantity - product.stock;
            System.out.println("Subtraktion von Lagerbestand für Artikel: " + product.id + " Menge: " + quantity
                    + " Lagerbestand: " + product.stock);

            ArrayList<WaitingListProduct> waitingListProducts = WaitingListProductsDB.getWaitingListProductsFromDB();
            int waitingQuantity = WaitingListProduct.GetWaitingListQuantity(product.id, waitingListProducts);
            System.out.println("Folgende Anzahl wurde für Produkt: " + product.id + " in der Warteliste gefunden: "
                    + waitingQuantity);

            quantity = quantity - waitingQuantity;
            System.out.println("Subtraktion für Warteliste für Artikel: " + product.id + " Menge: " + quantity
                    + " Warteliste: " + waitingQuantity);
            System.out.println("Finaler Produktionsmengen für Artikel: " + product.id + " Menge: " + quantity);
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
