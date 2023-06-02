package de.Ibsys.ibsys.Ordering;

import de.Ibsys.ibsys.database.GetProducts;

import java.util.ArrayList;

public class Calculations {

    public static ArrayList<NewOrder> createOrdersByProductionPlanning(
            ArrayList<ProductionPlanEntity> proudctionPlans) {

        ArrayList<NewOrder> orders = new ArrayList<>();

        System.out.println("Für Bestellungen zuerst alle Produkte die Bestellt werden können aus DB holen ");
        System.out.println("----------------------");

        ArrayList<Product> products = GetProducts.getProducts();

        System.out.println("Für Bestellungen von jedem Produkt den Lagerbestandverlauf updaten  ");
        System.out.println("dafür den Lagerbestandsverlauf anhand der noch offenen Bestellungen updaten ");
        System.out.println(
                "anschließend anhand des Produktionsplans und dem momentanen Bestand den Lagerbestandsverlauf über die nächsten Perioden updaten ");
        System.out.println("----------------------");
        System.out.println("Zuletzt die Bestellung erstellen, abhängig vom Produkt und Lagerbestand ");
        System.out.println("----------------------");

        for (Product product : products) {

            // update stockHistorieByOrders
            product.stockHistory = Order.updateStockHistoryByOrders(product);

            // update stockHistoryByForecast
            product.stockHistory = Product.updateStockHistoryByForecast(product, proudctionPlans);

            // create orders for the product
            NewOrder order = NewOrder.createOrder(product);
            if (order != null) {
                orders.add(order);
            }
        }

        System.out.println("Folgende Bestellungen wurden für diese Periode vorgeschlagen: ");

        for (NewOrder order : orders) {
            System.out.println("Article: " + order.getArticle());
            System.out.println("Quantity: " + order.getQuantity());
            System.out.println("Modus: " + order.getModus());
            System.out.println("----------------------");
        }

        System.out.println("Für Berechungen zuerst ");

        return orders;
    }
}
