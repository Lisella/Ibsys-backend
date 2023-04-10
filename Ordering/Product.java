package Ordering;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    int id;
    String name;
    int deliverytime;
    int variance;
    int product1Consumption;
    int product2Consumption;
    int product3Consumption;
    int discountQuantity;
    HashMap<Integer, Integer> stockHistory;

    // create constructor
    public Product(int id, String name, int discountQuantity, int deliverytime, int variance,
            int product1Consumption, int product2Consumption, int product3Consumption, int stock) {
        this.id = id;
        this.name = name;
        this.discountQuantity = discountQuantity;
        this.deliverytime = deliverytime;
        this.variance = variance;
        this.product1Consumption = product1Consumption;
        this.product2Consumption = product2Consumption;
        this.product3Consumption = product3Consumption;
        this.stockHistory = new HashMap<Integer, Integer>();

        for (int i = 0; i < 28; i++) {
            stockHistory.put(i, stock);
        }
    }

    public static ArrayList<Product> getProducts() {

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(21, "test", 300, 9, 2, 1, 0, 0, 340));
        // products.add(new Product(22, "test2", 2000, 10, 2, 2000, 2000, 2000, 2000));

        return products;
    }

    public static HashMap<Integer, Integer> updateStockHistoryByForecast(Product product,
            ArrayList<Forecast> forecast) {
        ArrayList<Integer> needsforWeek = Forecast.calcNeedsForWeek(product, forecast);
        // iterate over products.StockHistory and remove needs for each day from stock
        // value
        for (int i = 0; i < needsforWeek.size(); i++) {

            int amount = 0;
            if (needsforWeek.get(i) != 0) {
                amount = needsforWeek.get(i) / 5;
            }
            for (int j = 0; j < 5; j++) {

                // update the stock history foreach key, that is higher than the acutal i*5+j
                for (int k = i * 5 + j; k < 28; k++) {
                    product.stockHistory.put(k, product.stockHistory.get(k) - amount);
                }
            }
        }
        return product.stockHistory;
    }
}
