package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.FutureIncomingOrders.FutureOrder;
import de.Ibsys.ibsys.InputXml.Item;
import de.Ibsys.ibsys.Ordering.Order;
import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.database.ForecastsDB;
import de.Ibsys.ibsys.database.OrdersDB;
import de.Ibsys.ibsys.database.ProductsDB;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class XMLController {

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/in")
    public String parseJson(@RequestBody Map<String, Object> requestBody) {

        List<Map<String, Object>> articles = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                .get("warehousestock"))
                .get("articles");
        HashMap<Integer, Integer> articlesMap = new HashMap<>();

        for (Map<String, Object> article : articles) {
            if (article != null) {
                Integer id = Integer.parseInt((String) article.get("id"));
                Integer amount = Integer.parseInt((String) article.get("amount"));
                articlesMap.put(id, amount);
            }
        }

        ProductsDB.updateProductStock(articlesMap);

        List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) requestBody
                .get("waitinglistworkstations");

        HashMap<Integer, Integer> workstations = new HashMap<>();

        for (Map<String, Object> workstation : waitingListWorkstations) {
            if (workstation != null) {
                Integer id = Integer.parseInt((String) workstation.get("id"));
                Integer timeNeed = Integer.parseInt((String) workstation.get("timeneed"));
                workstations.put(id, timeNeed);
            }
        }

        // gebe alle Werte der HashMap in der Console aus
        System.out.println("Warteliste der jeweiligen Arbeitsplätze:");
        for (Map.Entry<Integer, Integer> entry : workstations.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // WaitingListForWorkstationsDB.updateWaitingListForWorkstations(workstations);
        System.out.println(articlesMap);
        System.out.println(workstations);

        // SChreibe in die Console, dass die Forecast speichern beginnt
        System.out.println("Speichere Forecast");
        Map<String, Object> forecast = (Map<String, Object>) requestBody
                .get("forecast");

        // create a Hashmap to store the forecast
        // use the amount for p1 and set the index to 1
        HashMap<Integer, Integer> forecastMap = new HashMap<>();
        forecastMap.put(1, Integer.parseInt((String) forecast.get("p1")));

        // use the amount for p2 and set the index to 2
        forecastMap.put(2, Integer.parseInt((String) forecast.get("p2")));
        // use the amount for p3 and set the index to 3
        forecastMap.put(3, Integer.parseInt((String) forecast.get("p3")));

        // Gebe die Hashmap in der Console aus
        System.out.println(forecastMap);

        // Rufe die Datenbank Methode auf um die Forecasts zu speichern
        ForecastsDB.updateForecasts(forecastMap);
        System.out.println("Forecast gespeichert");

        // Beginne mit der Speicherung der offenen Bestellungen
        System.out.println("Beginne mit der Verarbeitung offene Bestellungen");
        List<Map<String, Object>> orders = (List<Map<String, Object>>) requestBody
                .get("futureinwardstockmovement");

        // Gebe die offenen Bestellungen in der Console aus
        System.out.println(orders);

        // Hole alle Produkte aus der DB um die Lieferdauer zu bestimmen
        ArrayList<Product> products = ProductsDB.getProducts();
        System.out.println(products);

        List<FutureOrder> futureOrders = new ArrayList<>();
        for (Map<String, Object> order : orders) {
            int productId = Integer.parseInt(order.get("article").toString());
            int quantity = Integer.parseInt(order.get("amount").toString());
            // Berechne die Tage in denen die Bestellung spätestens ankommt
            Product product = getProductByProductID(productId, products);
            Map<String, Object> overview = (Map<String, Object>) requestBody.get("overview");
            int period = Integer.parseInt((String) overview.get("period"));
            int orderPeriode = Integer.parseInt(order.get("orderperiod").toString());
            int periodDifference = period + 1 - orderPeriode;
            System.out.println("Vergangene Perioden: " + periodDifference);
            int maxDeliveryTime = product.getDeliveryTime();
            int mode = Integer.parseInt(order.get("mode").toString());
            int daysAfterToday = 0;
            if (mode == 4) {
                daysAfterToday = maxDeliveryTime / 2 - 5 * periodDifference;
            }
            if (mode != 4) {
                daysAfterToday = maxDeliveryTime - 5 * periodDifference;
            }

            FutureOrder futureOrder = new FutureOrder(productId, quantity, daysAfterToday);
            futureOrders.add(futureOrder);
        }

        ArrayList<Order> ordersDb = new ArrayList<Order>();

        // Now, you have a list of FutureOrder objects
        for (FutureOrder order : futureOrders) {
            // Create an Order object
            Order orderDb = new Order(
                    ordersDb.size() + 1, order.getProductId(), order.getQuantity(), order.getDaysAfterToday());
            ordersDb.add(orderDb);
            System.out.println("Product ID: " + order.getProductId() + ", Quantity: " + order.getQuantity()
                    + ", Days After Today: " + order.getDaysAfterToday());
        }

        // speicher die offenen Bestellungen in der Datenbank
        OrdersDB.putOrders(ordersDb);
        return "Ok";
    }

    // Create a Methode, that returns the product by ProductId
    public static Product getProductByProductID(int productID, ArrayList<Product> products) {
        for (Product product : products) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }
}
