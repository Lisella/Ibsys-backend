package Ordering;

import java.util.HashMap;

public class Product {
    int id;
    String name;
    int deliverytime;
    double variance;
    int product1Consumption;
    int product2Consumption;
    int product3Consumption;
    int discountQuantity;
    HashMap<Integer, Integer> stockHistory;

    // create constructor
    public Product(int id, String name, int discountQuantity, int deliverytime, double variance,
            int product1Consumption, int product2Consumption, int product3Consumption, int stock,
            Integer daysAfterToday) {
        this.id = id;
        this.name = name;
        this.discountQuantity = discountQuantity;
        this.deliverytime = deliverytime;
        this.variance = variance;
        this.product1Consumption = product1Consumption;
        this.product2Consumption = product2Consumption;
        this.product3Consumption = product3Consumption;
        this.stockHistory = new HashMap<Integer, Integer>();
        this.stockHistory.put(daysAfterToday, stock);

        for (int i = 0; i < 28; i++) {
            stockHistory.put(i, stock);
        }
    }
}
