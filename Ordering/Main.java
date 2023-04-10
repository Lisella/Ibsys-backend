package Ordering;

import java.util.ArrayList;

public class Main {

    public static ArrayList<Product> getProducts() {

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(21, "test", 2000, 9, 2, 1, 0, 0, 340, 5));
        // products.add(new Product(22, "test2", 2000, 10, 2, 2000, 2000, 2000, 2000,
        // LocalDate.now()));
        return products;
    }

    public static ArrayList<Order> getOrders() {

        ArrayList<Order> orders = new ArrayList<>();
        // orders.add(new Order(1, 21, 2000, LocalDate.now().plusDays(5)));

        return orders;
    }

    /**
     * @return
     */
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
            // iterate over products.StockHistory and remove needs for each day from stock
            // value
            for (int i = 0; i < needsforWeek.size(); i++) {

                int amount = 0;
                if (needsforWeek.get(i) != 0) {
                    amount = needsforWeek.get(i) / 5;
                }
                for (int j = 0; j < 5; j++) {
                    int stock = product.stockHistory.get(i * 5 + j);
                    // update the stock history foreach key, that is higher than the acutal i*5+j
                    for (int k = i * 5 + j; k < 28; k++) {
                        product.stockHistory.put(k, stock - amount);
                    }

                }
            }
        }
        System.out.println(products.get(0).stockHistory);
        return orders;

    }

    public static ArrayList<Integer> calcNeedsForWeek(Product product, ArrayList<Forecast> forecast) {
        ArrayList<Integer> needs = new ArrayList<>();
        for (Forecast f : forecast) {
            needs.add(f.product1Consumption * product.product1Consumption
                    + f.product2Consumption * product.product2Consumption
                    + f.product3Consumption * product.product3Consumption);
        }
        return needs;
    }

    public static void main(String[] args) {
        createOrders();

        // System.out.println(products.get(0).stockHistory);
    }
}
