import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Product> getProducts() {

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(21, "test", 2000, 10, 2, 2000, 2000, 2000, 3000, LocalDate.now()));
        products.add(new Product(22, "test2", 2000, 10, 2, 2000, 2000, 2000, 2000, LocalDate.now()));
        return products;
    }

    public static ArrayList<Order> getOrders() {

        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(1, 21, 2000, LocalDate.now().plusDays(5)));

        return orders;
    }

    // create a methode that adds a array to a product with a key of a localDate
    // with the given date and goes for the next 4 weeks without the sundays
    // the value of the key is the stock value of the product
    public void createStockHistory(Product product, ArrayList<Order> orders) {

    }

    public static void main(String[] args) {

        ArrayList<Product> products = getProducts();
        ArrayList<Order> orders = getOrders();
        ArrayList<Forecast> forecast = Forecast.getForecast();

        System.out.println(products.get(0).stockHistory);
    }
}
