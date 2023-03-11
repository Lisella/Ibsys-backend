import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static List<Product> getProducts() {

        List<Product> products = new ArrayList<>();
        products.add(new Product(21, "test", 2000, 10, 2, 2000, 2000, 2000, 3000, LocalDate.now()));
        products.add(new Product(22, "test2", 2000, 10, 2, 2000, 2000, 2000, 2000, LocalDate.now()));
        return products;
    }

    public static List<Order> getOrders() {

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, 21, 2000, LocalDate.now().plusDays(5)));
        orders.add(new Order(1, 21, 2000, LocalDate.now().plusDays(20)));

        return orders;
    }

    // create a methode that adds a array to a product with a key of a localDate
    // with the given date and goes for the next 4 weeks without the sundays
    // the value of the key is the stock value of the product
    public static void createStockHistory(Product product, List<Order> orders) {
        // filter orders by product productId
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.productId == product.id) {
                filteredOrders.add(order);
            }
        }

        // order filteredOrders by date
        Collections.sort(filteredOrders, new Comparator<Order>() {
            public int compare(Order o1, Order o2) {
                return o1.date.compareTo(o2.date);
            }
        });

        for (Order order : filteredOrders) {
            // for each stock History entry if the date is after the order date add the
            // order quantity to the stock
            for (LocalDate date : product.stockHistory.keySet()) {
                if (date.isAfter(order.date) || date.isEqual(order.date)) {
                    product.stockHistory.put(date, product.stockHistory.get(date) + order.quantity);
                }
            }
        }
    }

    public static void main(String[] args) {

        List<Product> products = getProducts();
        List<Order> orders = getOrders();
        System.out.println(products.get(0).stockHistory);

        // foreach products create a stockHistory
        for (Product product : products) {
            // createStockHistory(product, orders);
            createStockHistory(product, orders);
        }

        System.out.println("--------------------");
        System.out.println(products.get(0).stockHistory);
    }
}
