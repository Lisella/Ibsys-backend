package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.FutureIncomingOrders.FutureOrder;
import de.Ibsys.ibsys.InputXml.Item;
import de.Ibsys.ibsys.Ordering.Order;
import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.Production.WaitingListProduct;
import de.Ibsys.ibsys.database.*;

import de.Ibsys.ibsys.database.WaitingListForWorkstationsDB;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class XMLController {

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/in")
    public String parseJson(@RequestBody Map<String, Object> requestBody) {


        /**
         * Speichere alle Artikel in der Datenbank
         */

        List<Map<String, Object>> articles = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                .get("warehousestock"))
                .get("articles");
        HashMap<Integer, Integer> articlesMap = new HashMap<>();

        for (Map<String, Object> article : articles) {
            if (article != null) {
                Integer id = Integer.parseInt(article.get("id").toString());
                Integer amount = Integer.parseInt( article.get("amount").toString());
                articlesMap.put(id, amount);
            }
        }

        ProductsDB.updateProductStock(articlesMap);




        /**
         * Speichere alle Production Products in der Datenbank (nur die gültigen)
         */

        HashMap<Integer, Integer> productionProductsMap = new HashMap<>();

        List<Integer> validProductIds = List.of(
                4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 26, 49, 54, 29, 50, 55,
                30, 51, 56, 31, 1, 2, 3
        );


        for (Map<String, Object> article : articles) {
            if (article != null) {
                String idString = article.get("id").toString();
                Integer amount = Integer.parseInt(article.get("amount").toString());

                Integer id = Integer.parseInt(idString);

                if (validProductIds.contains(idString)) {
                    productionProductsMap.put(id, amount);
                }
            }
        }

        ProductionProductsDB.updateProductionProductsStock(productionProductsMap);



        /**
         * Ermittle Workstation und verbleibende Arbeitszeit
         */

        List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) requestBody
                .get("waitinglistworkstations");

        HashMap<Integer, Integer> workstations = new HashMap<>();
        ArrayList<WaitingListProduct> waitingListProducts = new ArrayList<>();

        for (Map<String, Object> workstation : waitingListWorkstations) {
            if (workstation != null) {
                Integer id = Integer.parseInt((workstation.get("id").toString()));
                Integer timeNeed = Integer.parseInt((String) workstation.get("timeneed"));
                workstations.put(id, timeNeed);
            }



            /**
             * Ermittle Produktnummer und Menge der Produkte in der Warteliste
             */

            List<Map<String, Object>> waitingLists = (List<Map<String, Object>>) workstation.get("waitingslists");
            if (waitingLists != null) {
                for (Map<String, Object> waitingList : waitingLists) {
                    Integer item = Integer.parseInt((waitingList.get("item").toString()));
                    Integer amount = Integer.parseInt((waitingList.get("amount").toString()));

                    if (item != null && amount != null) {
                        waitingListProducts.add(new WaitingListProduct(item, amount));
                    }
                }
            }

            System.out.println();
            System.out.println(" *** Warteliste der jeweiligen Arbeitsplätze *** ");
            System.out.println();

            for (Map.Entry<Integer, Integer> entry : workstations.entrySet()) {
                System.out.println(" Produktnummer: " + entry.getKey() + ":" + entry.getValue() + " Stück");
            }

            WaitingListForWorkstationsDB.updateWaitingListForWorkstations(workstations);


            System.out.println();
            System.out.println(" *** Ermittlung der Produkte die sich in der Warteschlange befinden *** ");
            System.out.println();

            for (WaitingListProduct waitingListProduct : waitingListProducts){
                System.out.println("Product Nr: " + waitingListProduct.getProductId() + waitingListProduct.getQuantity() + " : Stück");
            }

            WaitingListProductsDB.updateWaitingListProducts(waitingListProducts);


            System.out.println();
            System.out.println(" *** Ermittlung der Produkte die sich in der Warteschlange befinden *** ");
            System.out.println();
            Map<String, Object> forecast = (Map<String, Object>) requestBody.get("forecast");

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

            System.out.println();
            System.out.println("Forecast gespeichert");
            System.out.println();
            System.out.println("*** Beginne mit der Verarbeitung offene Bestellungen ***");
            System.out.println();


            List<Map<String, Object>> orders = (List<Map<String, Object>>) requestBody.get("futureinwardstockmovement");

            System.out.println();
            System.out.println("*** eingehende Lagerbewegungen ***");
            System.out.println();

            System.out.println("Bestellungen:");
            for (Map<String, Object> order : orders) {
                System.out.println("Bestellung: " + order);
            }


            ArrayList<Product> products = ProductsDB.getProducts();

           /* System.out.println();
            System.out.println("*** Alle Produkte in der Datenbank ***");
            System.out.println();

            System.out.println("Produkte:");
            for (Product product : products) {
                System.out.println("Produkt: " + product);
            }*/


            List<FutureOrder> futureOrders = new ArrayList<>();
            for (Map<String, Object> order : orders) {
                int productId = Integer.parseInt(order.get("article").toString());
                int quantity = Integer.parseInt(order.get("amount").toString());
                // Berechne die Tage in denen die Bestellung spätestens ankommt

                Product product = null;
                for (Product p : products) {
                    if (p.getId() == productId) {
                        product = p;
                        break;
                    }
                }

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
        }

        System.out.println();
        System.out.println(" *** Ermittlung aller Artikel aus dem Warenbestand *** ");
        System.out.println();

        for (Map.Entry<Integer, Integer> entry : articlesMap.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();
            System.out.println("Product Nr: " + productId + " - Menge: " + quantity + " Stück");
        }


        System.out.println();
        System.out.println(" *** Ermittlung der ProductionProducts und ihr Warenbestand *** ");
        System.out.println();

        for (Map.Entry<Integer, Integer> entry : productionProductsMap.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();
            System.out.println("Product Nr: " + productId + " - Menge: " + quantity + " Stück");
        }


        return "Ok";



}}
