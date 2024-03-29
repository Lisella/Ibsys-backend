package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.FutureIncomingOrders.FutureOrder;
import de.Ibsys.ibsys.Ordering.Order;
import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.Production.WaitingListProduct;
import de.Ibsys.ibsys.database.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class XMLController {

    private HashMap<Integer, Integer> articlesMap;
    private HashMap<Integer, Integer> productionProductsMap;
    private HashMap<Integer, Integer> workstations;
    private ArrayList<WaitingListProduct> waitingListProducts;

    /**
     * REST Endpoint for the XML-Input
     **/
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/in")
    public String parseJson(@RequestBody Map<String, Object> requestBody) {
        parseWarehouseStock(requestBody);
        parseProductionProducts(requestBody);
        parseWorkstations(requestBody);
        parseWaitingList(requestBody);
        parseForecast(requestBody);
        processOrders(requestBody);
        printResults();
        return "Ok";
    }

    /**
     * Ermittlung aller Produkte im Warenlager
     **/

    private void parseWarehouseStock(Map<String, Object> requestBody) {
        List<Map<String, Object>> articles = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                .get("warehousestock"))
                .get("articles");
        articlesMap = new HashMap<>();
        for (Map<String, Object> article : articles) {
            if (article != null) {
                Integer id = Integer.parseInt(article.get("id").toString());
                Integer amount = Integer.parseInt(article.get("amount").toString());
                articlesMap.put(id, amount);
            }
        }
        ProductsDB.updateProductStock(articlesMap);
    }

    /**
     * Ermittlung aller ProductionProducts im Warenlager
     **/

    private void parseProductionProducts(Map<String, Object> requestBody) {
        List<Map<String, Object>> articles = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                .get("warehousestock"))
                .get("articles");
        productionProductsMap = new HashMap<>();
        List<Integer> validProductIds = List.of(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 26, 29, 30, 31, 49, 50, 51, 54,
                55, 56);
        for (Map<String, Object> article : articles) {
            if (article != null) {
                Integer id = Integer.parseInt(article.get("id").toString());
                Integer amount = Integer.parseInt(article.get("amount").toString());
                if (validProductIds.contains(id)) {
                    productionProductsMap.put(id, amount);
                }
            }
        }
        ProductionProductsDB.updateProductionProductsStock(productionProductsMap);
    }

    /**
     * Ermittlung aller ProductionProducts im Warenlager
     **/

    private void parseWorkstations(Map<String, Object> requestBody) {
        List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) requestBody
                .get("waitinglistworkstations");
        workstations = new HashMap<>();
        for (Map<String, Object> workstation : waitingListWorkstations) {
            if (workstation != null) {
                Integer id = Integer.parseInt((workstation.get("id").toString()));
                Integer timeNeed = Integer.parseInt((String) workstation.get("timeneed"));
                workstations.put(id, timeNeed);
            }
        }
        WaitingListForWorkstationsDB.updateWaitingListForWorkstations(workstations);
    }

    private void parseWaitingList(Map<String, Object> requestBody) {
        waitingListProducts = new ArrayList<>();
        List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) requestBody
                .get("waitinglistworkstations");
        List<Map<String, Object>> waitingListStock = (List<Map<String, Object>>) requestBody.get("waitingliststock");

        // Für Aufträge, die wegen Maschinen nicht angefangen wurden
        for (Map<String, Object> workstation : waitingListWorkstations) {
            List<Map<String, Object>> waitingLists = (List<Map<String, Object>>) workstation.get("waitingslists");
            if (waitingLists != null) {
                for (Map<String, Object> waitingList : waitingLists) {
                    Integer item = Integer.parseInt(waitingList.get("item").toString());
                    Integer waitlistQuantity = Integer.parseInt(waitingList.get("amount").toString());
                    waitingListProducts.add(new WaitingListProduct(item, waitlistQuantity, 0));
                }
            }
        }

        // Für Aufträge die wegen Fehledem Material nicht gemacht werden
        for (Map<String, Object> stockItem : waitingListStock) {
            List<Map<String, Object>> workplaces = (List<Map<String, Object>>) stockItem.get("workplaces");
            for (Map<String, Object> workplace : workplaces) {
                List<Map<String, Object>> waitingLists = (List<Map<String, Object>>) workplace.get("waitinglists");
                for (Map<String, Object> waitingList : waitingLists) {
                    Integer item = Integer.parseInt(waitingList.get("item").toString());
                    Integer waitlistQuantity = Integer.parseInt(waitingList.get("amount").toString());
                    System.out.println("Folgendes Item wegen Material" + item);

                    waitingListProducts.add(new WaitingListProduct(item, waitlistQuantity, 0));
                }
            }
        }

        List<Map<String, Object>> ordersInWorkList = (List<Map<String, Object>>) requestBody.get("ordersinswork");

        // Verarbeitung in Produktion
        for (Map<String, Object> orderInWork : ordersInWorkList) {
            Integer item = Integer.parseInt(orderInWork.get("item").toString());
            Integer inworkQuantity = Integer.parseInt(orderInWork.get("amount").toString());

            waitingListProducts.add(new WaitingListProduct(item, 0, inworkQuantity));
        }

        System.out.println("Aktuelle Wartelisten:");
        ArrayList<WaitingListProduct> mergedList = mergeWaitingListProducts(waitingListProducts);
        for (WaitingListProduct w : mergedList) {
            System.out.println(w.getProductId());
        }

        System.out.println("Alle ausgegeben: ");
        WaitingListProductsDB.putWaitingListProducts(mergedList);
    }

    private ArrayList<WaitingListProduct> mergeWaitingListProducts(List<WaitingListProduct> productList) {
        Map<Integer, WaitingListProduct> mergedMap = new HashMap<>();
        for (WaitingListProduct product : productList) {
            int productId = product.getProductId();
            int quantity = product.getWaitlistQuantity();
            int inWorkQuantity = product.getInWorkQuantity();

            if (mergedMap.containsKey(productId)) {
                WaitingListProduct existingProduct = mergedMap.get(productId);
                existingProduct.setWaitlistQuantity((existingProduct.getWaitlistQuantity() + quantity));
                existingProduct.setInWorkQuantity(existingProduct.getInWorkQuantity() + inWorkQuantity);
            } else {
                mergedMap.put(productId, new WaitingListProduct(productId, quantity, inWorkQuantity));
            }
        }
        return new ArrayList<>(mergedMap.values());
    }

    private void parseForecast(Map<String, Object> requestBody) {
        Map<String, Object> forecast = (Map<String, Object>) requestBody.get("forecast");
        HashMap<Integer, Integer> forecastMap = new HashMap<>();
        forecastMap.put(1, Integer.parseInt((String) forecast.get("p1")));
        forecastMap.put(2, Integer.parseInt((String) forecast.get("p2")));
        forecastMap.put(3, Integer.parseInt((String) forecast.get("p3")));
        ForecastsDB.updateForecasts(forecastMap);
    }

    private void processOrders(Map<String, Object> requestBody) {
        List<Map<String, Object>> orders = (List<Map<String, Object>>) requestBody.get("futureinwardstockmovement");
        ArrayList<Product> products = ProductsDB.getProducts();
        ArrayList<FutureOrder> futureOrders = new ArrayList<>();
        for (Map<String, Object> order : orders) {
            int productId = Integer.parseInt(order.get("article").toString());
            int quantity = Integer.parseInt(order.get("amount").toString());
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
        ArrayList<Order> ordersDb = new ArrayList<>();
        for (FutureOrder order : futureOrders) {
            Order orderDb = new Order(
                    ordersDb.size() + 1, order.getProductId(), order.getQuantity(), order.getDaysAfterToday());
            ordersDb.add(orderDb);
        }
        OrdersDB.putOrders(ordersDb);
    }

    private void printResults() {
        System.out.println();
        System.out.println(" *** Ermittlung aller Artikel aus dem Warenbestand *** ");
        System.out.println();
        for (Map.Entry<Integer, Integer> entry : articlesMap.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();
            System.out.println("Produktnummer: " + productId + " - Menge: " + quantity + " Stück");
        }
        System.out.println();
        System.out.println(" *** Ermittlung der ProductionProducts und ihr Warenbestand *** ");
        System.out.println();
        for (Map.Entry<Integer, Integer> entry : productionProductsMap.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();
            System.out.println("Produktnummer: " + productId + " - Menge: " + quantity + " Stück");
        }
        System.out.println();
        System.out.println(" *** In Produktion befindliche ProductionProducts *** ");
        System.out.println();
        for (Map.Entry<Integer, Integer> entry : workstations.entrySet()) {
            System.out.println("Produktnummer: " + entry.getKey() + ": " + entry.getValue() + " Stück");
        }
        System.out.println();
        System.out.println(" *** Ermittlung der Produkte die sich in der Warteschlange befinden *** ");
        System.out.println();
        for (WaitingListProduct waitingListProduct : waitingListProducts) {
            if (waitingListProduct.getWaitlistQuantity() != 0) {
                System.out.println("Produktnummer: " + waitingListProduct.getProductId() + " - "
                        + waitingListProduct.getWaitlistQuantity() + " Stück");
            }
        }
        System.out.println();
        System.out.println(" *** Ermittlung der Produkte die sich in Bearbeitung befinden *** ");
        System.out.println();
        for (WaitingListProduct waitingListProduct : waitingListProducts) {
            if (waitingListProduct.getInWorkQuantity() != 0) {
                System.out.println("Produktnummer: " + waitingListProduct.getProductId() + " - "
                        + waitingListProduct.getInWorkQuantity() + " Stück");
            }
        }

    }

}
