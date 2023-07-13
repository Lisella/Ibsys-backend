package de.Ibsys.ibsys.Production;

import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import de.Ibsys.ibsys.database.ProductionProductsDB;
import de.Ibsys.ibsys.database.WaitingListProductsDB;

import java.util.ArrayList;

public class Calculations {

    public static ArrayList<ProductionItem> createProductionByProductionPlanning(
            ArrayList<ProductionPlanEntity> productionPlan, ArrayList<ReserveStockProduct> reserveStockProducts) {

        ArrayList<ProductionItem> productionItems = new ArrayList<>();
        ArrayList<WaitingListProduct> waitingListProducts = WaitingListProductsDB.getWaitingListProductsFromDB();
        System.out.println("Für Produktionmengen zuerst alle Produkte die nicht bestellt werden können aus DB holen ");
        System.out.println("----------------------");

        ArrayList<ProductionProduct> products = ProductionProductsDB.getProductionProducts();

        // gebe alle Produkte aus, mit dem aktuellen Lagerbestand
        for (ProductionProduct product : products) {
            for (ReserveStockProduct r : reserveStockProducts) {
                if (product.getId() == r.getProductId()) {
                    for (WaitingListProduct w : waitingListProducts) {
                        if (product.getId() == w.getProductId()) {
                            product.setWaitingListQuantity(w.getWaitlistQuantity());
                            product.setInProductionQuantity(w.getInWorkQuantity());
                            product.setReserveStock(r.getReserveStock());
                        }
                    }
                }
            }
        }

        // berechne Produktionsmengen für jeden Artikel
        for (ProductionProduct product : products) {
            int requiredQuantity = calculateRequiredQuantity(product, productionPlan);
            productionItems.add(new ProductionItem(product.getId(), product.getName(), requiredQuantity));
        }

        // setzte die Sequenznummer für die Produktionsmengen
        for (int i = 0; i < productionItems.size(); i++) {
            ProductionItem item = productionItems.get(i);
            item.setSequenceNumber(i + 1);
            item.setId(i + 1);
        }

        printProductionItems(productionItems);

        System.out.println("Produktionsmengen Berechnung erfolgreich");

        return productionItems;
    }

    private static int calculateRequiredQuantity(ProductionProduct product, ArrayList<ProductionPlanEntity> productionPlan) {
        int requiredQuantity = 0;
        for (ProductionPlanEntity planEntity : productionPlan) {
            switch (product.getId()) {
                case 1:
                    requiredQuantity += planEntity.getProduct1Consumption();
                    break;
                case 2:
                    requiredQuantity += planEntity.getProduct2Consumption();
                    break;
                case 3:
                    requiredQuantity += planEntity.getProduct3Consumption();
                    break;
                default:
                    break;
            }
        }
        requiredQuantity += product.getReserveStock() - product.getStock() - product.getWaitingListQuantity()
                - product.getInProductionQuantity();
        return requiredQuantity;
    }

    private static void printProductionItems(ArrayList<ProductionItem> productionItems) {
        System.out.println("----------------------");
        System.out.println("Folgende Produktionsmengen werden vorgeschlagen:");
        System.out.println("----------------------");
        for (ProductionItem productionItem : productionItems) {
            System.out.println("Article: " + productionItem.getArticle());
            System.out.println("Quantity: " + productionItem.getQuantity());
            System.out.println("Reihenfolge : " + productionItem.getSequenceNumer());
            System.out.println("----------------------");
        }
    }

    private static ProductionProduct getProductById(int id, ArrayList<ProductionProduct> products) {
        for (ProductionProduct product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}
