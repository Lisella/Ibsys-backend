package de.Ibsys.ibsys.Ordering;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewOrder {
    private int article;
    private String name;
    private int quantity;
    private int modus;
    private ArrayList<String> orderInfos;

    @JsonCreator
    public NewOrder(@JsonProperty("article") int article,
            String name,
            @JsonProperty("quantity") int quantity,
            @JsonProperty("modus") int modus,
            @JsonProperty("orderInfos") ArrayList<String> orderInfos) {
        this.article = article;
        this.quantity = quantity;
        this.modus = modus;
        this.name = name;
        this.orderInfos = orderInfos;
    }

    // Getters and setters

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getModus() {
        return modus;
    }

    public void setModus(int modus) {
        this.modus = modus;
    }

    public void setOrderInfos(ArrayList<String> orderInfos) {
        this.orderInfos = orderInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static NewOrder createOrder(Product product) {
        // Suche den Tag, an dem der Bestand ausgeht (product.stockHistory.get(i) < 0)
        // erstelle eine bestellung für dieses Produkt
        // setze die Menge auf product.discountQuantity
        // erhöhe den Bestand für alle Tage ab dem Tag der Bestellung um
        // product.discountQuantity
        // wiederhole das ganze für alle Tage, an denen der Bestand ausgeht

        ArrayList<NewOrder> orders = new ArrayList<NewOrder>();

        // orderInfos enthält alle Consolenausgaben um im Frontend berechnungen
        // nachvollziehen zu können.
        ArrayList<String> orderInfos = new ArrayList<String>();

        System.out.println("----------------------");
        System.out.println("Berechne Bestellungen für Produkt: " + product.id);
        System.out.println("Lagerbestandsverlauf: " + product.stockHistory);
        int maxDeliveryTime = product.deliverytime + product.variance;

        orderInfos.add("Maximale Lieferdauer: " + maxDeliveryTime);
        orderInfos.add("Lagerbestandsverlauf: " + product.stockHistory);

        System.out.println("Maximale Lieferdauer: " + maxDeliveryTime);

        for (int i = 0; i < 20; i++) {

            if (product.stockHistory.get(i + 1) < 0 && product.stockHistory.get(i + 2) < 0 && product.stockHistory
                    .get(i + 3) < 0 && product.stockHistory.get(i + 4) < 0) {

                int orderQuantity = product.discountQuantity;
                int orderDay = i - maxDeliveryTime;

                if (orderDay < 6) {
                    System.out.println("Produkt geht an Tag " + i + " aus. Bestellung erfolgt an Tag: " + orderDay);
                    orderInfos.add("Produkt geht an Tag " + i + " aus. Bestellung erfolgt an Tag: " + orderDay);
                }

                // Lagerbestand erhöhen für alle Tage ab dem Tag an dem die Bestellung
                // spätestens ankommt

                for (int j = i; j < 27; j++) {
                    product.stockHistory.put(j, product.stockHistory.get(j) + orderQuantity);
                    if (j == 26)
                        System.out.println("Neuer Lagerbestand: " + product.stockHistory);
                }

                // wenn Order Day ist in aktueller Periode (0-5)
                if (orderDay >= 0 && orderDay < 5) {
                    orders.add(new NewOrder(product.id, product.name, orderQuantity, 5, orderInfos));
                    System.out.println("Neue Normale Bestellung: Produkt: " + product.id + " Menge: " + orderQuantity
                            + " Modus: 5");
                    orderInfos.add("Neue Normale Bestellung: Produkt: " + product.id + " Menge: " + orderQuantity
                            + " Modus: 5");
                    orderInfos.add("Finaler Lagerbestandsverlauf: " + product.stockHistory);

                }

                // wenn Order Day ist in nächster Periode (5-10) mache eine günstige Bestellung
                /*
                 * else if (orderDay >= 5 && orderDay < 10) {
                 * orders.add(new NewOrder(product.id, orderQuantity, 4));
                 * System.out.println("Neue Günstige Bestellung: Produkt: " + product.id +
                 * " Menge: " + orderQuantity
                 * + " Modus: 4");
                 * }
                 */
                // wenn Order Day ist in Vergangenheit, mache eine Schnelle Bestellung
                else if (orderDay < 0) {
                    orders.add(new NewOrder(product.id, product.name, orderQuantity, 4, orderInfos));
                    System.out.println("Neue Schnelle Bestellung: Produkt: " + product.id + " Menge: " + orderQuantity
                            + " Modus: 4");
                    orderInfos.add("Neue Schnelle Bestellung: Produkt: " + product.id + " Menge: " + orderQuantity
                            + " Modus: 4");
                    orderInfos.add("Finaler Lagerbestandsverlauf: " + product.stockHistory);

                }
            }
        }

        // wenn beide Bestellungen in der gleichen Periode sind, dann mache eine daraus
        if (orders.size() > 1) {
            int amount = 0;

            for (NewOrder o : orders) {
                amount += o.getQuantity();
            }
            System.out.println("2 Bestellungen in einer Periode für Produkt: " + product.id);
            System.out.println("Bestellungen zusammenfassen. Neue Bestellmenge: " + amount);
            orderInfos.add("Beide Besellungen zu einer zusammengesfasst. Neue Bestellmenge: " + amount);
            orderInfos.add("Finaler Lagerbestandsverlauf: " + product.stockHistory);
            return new NewOrder(product.id, product.name, amount, orders.get(0).getModus(), orderInfos);
        }

        if (orders.size() == 0) {
            return null;
        }

        return orders.get(0);
    }

    public ArrayList<String> getOrderInfos() {
        return orderInfos;
    }

}
