package Ordering;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.LogManager;

public class Main {

    public static ArrayList<Product> getProducts() {

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(21, "test", 2000, 9, 2, 1, 0, 0, 340, LocalDate.now()));
        // products.add(new Product(22, "test2", 2000, 10, 2, 2000, 2000, 2000, 2000,
        // LocalDate.now()));
        return products;
    }

    public static ArrayList<Order> getOrders() {

        ArrayList<Order> orders = new ArrayList<>();
        // orders.add(new Order(1, 21, 2000, LocalDate.now().plusDays(5)));

        return orders;
    }

    public static ArrayList<NewOrder> createOrders() {

        ArrayList<NewOrder> orders = new ArrayList<>();

        // get Forecast
        ArrayList<Forecast> forecast = Forecast.getForecast();
        // log forecast

        // get StockHistories
        ArrayList<Product> products = getProducts();
        // log stockHistories

        // calc needs for each product
        for (Product product : products) {
            ArrayList<Integer> needsforWeek = calcNeedsForWeek(product, forecast);
            // log needs for week

            // remove needs for week / 5 from stock history for each day of the week

        }

        return orders;
    }

    public static ArrayList<Integer> calcNeedsForWeek(Product product, ArrayList<Forecast> forecast) {
        ArrayList<Integer> needs = new ArrayList<>();
        for (Forecast f : forecast) {
            needs.add(f.product1Consumption * product.product1Consumption
                    + f.product2Consumption * product.product2Consumption
                    + f.product3Consumption * product.product3Consumption);
        }

        System.out.println(needs);
        return needs;
    }

    public static void main(String[] args) {
        createOrders();

        // System.out.println(products.get(0).stockHistory);
    }
}
