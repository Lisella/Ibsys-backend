package Ordering;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    int id;
    int productId;
    int quantity;
    int daysAfterToday;

    // create consutructor
    public Order(int id, int productId, int quantity, int daysAfterToday) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.daysAfterToday = daysAfterToday;
    }

    public static ArrayList<Order> getOrders(int productId) {
        // todo return orders for productId
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(1, 21, 300, 1));
        return orders;
    }

    public static HashMap<Integer, Integer> updateStockHistoryByOrders(Product product) {
        // add orders to stock History
        for (Order o : Order.getOrders(product.id)) {

            for (int i = o.daysAfterToday; i < 28; i++) {
                product.stockHistory.put(i, product.stockHistory.get(i) + o.quantity);
            }
        }

        return product.stockHistory;
    }
}
