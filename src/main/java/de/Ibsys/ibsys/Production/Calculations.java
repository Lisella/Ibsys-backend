package de.Ibsys.ibsys.Production;

import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;

import java.util.ArrayList;

public class Calculations {

    public static ArrayList<ProductionItem> createProductionByProductionPlanning(ArrayList<ProductionPlanEntity> productionPlans, boolean splitting){

        ArrayList<ProductionItem> productionItems = new ArrayList<>();

        System.out.println("Für Produktionmengen zuerst alle Produkte die nicht bestellt werden können aus DB holen ");
        System.out.println("----------------------");

        ArrayList<ProductionProduct> products = ProductionProduct.getProductionProductsFromDB();

        System.out.println("Für Produktionsmengen den Produktverbrauch anhand der Produktionsmengen bestimmen");

        for (ProductionProduct product: products ){
            int quantity = product.product1Consumption * productionPlans.get(0).product1Consumption + product.product2Consumption * productionPlans.get(0).product2Consumption + product.product3Consumption * productionPlans.get(0).product3Consumption;
            productionItems.add(new ProductionItem(product.id, quantity));
        }

        if (splitting){
            // berechne optimale Losgröße für jeden Auftrag
            // wenn benötige Materialen verfügbar sind, dann nicht splitten, wenn nicht verfübar, dann so viele wie möglich und splitten

            // Für jeden Fertigungsauftrag
            for (ProductionItem productionItem: productionItems){

                // Finde zuerst die benötigten Ressourcen



                // wenn genug Ressourcen, dann tu nichts

                // wenn nicht genug Ressourcen, passe Produktionsmenge an und füge 2ten Auftrag ans Ende hinzu

            }
        }

        System.out.println("----------------------");
        System.out.println("Folgende Produktionsmengen werden vorgeschlagen:");
        System.out.println("----------------------");
        for (ProductionItem productionItem : productionItems) {
            System.out.println("Article: " + productionItem.getArticle());
            System.out.println("Quantity: " + productionItem.getQuantity());
            System.out.println("----------------------");
        }


        System.out.println("Produktionsmengen Berechnung erfolgreich");

        return productionItems;
    }
}
