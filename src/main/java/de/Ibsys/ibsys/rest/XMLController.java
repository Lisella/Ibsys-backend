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

    private HashMap<Integer, Integer> articlesMap;
    private HashMap<Integer, Integer> productionProductsMap;
    private HashMap<Integer, Integer> workstations;
    private ArrayList<WaitingListProduct> waitingListProducts;

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

    private void parseProductionProducts(Map<String, Object> requestBody) {
        List<Map<String, Object>> articles = (List<Map<String, Object>>) ((Map<String, Object>) requestBody
                .get("warehousestock"))
                .get("articles");
        productionProductsMap = new HashMap<>();
        List<Integer> validProductIds = List.of(
                4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
                16, 17, 18, 19, 20, 26, 49, 54, 29, 50, 55,
                30, 51, 56, 31, 1, 2, 3
        );
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
/*
    private void parseWaitingList(Map<String, Object> requestBody) {
        waitingListProducts = new ArrayList<>();
        List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) requestBody
                .get("waitinglistworkstations");
        for (Map<String, Object> workstation : waitingListWorkstations) {
            List<Map<String, Object>> waitingLists = (List<Map<String, Object>>) workstation.get("waitinglist");
            if (waitingLists != null) {
                for (Map<String, Object> waitingList : waitingLists) {
                    Integer item = Integer.parseInt((waitingList.get("item").toString()));
                    Integer amount = Integer.parseInt((waitingList.get("amount").toString()));
                    if (item != null && amount != null) {
                        waitingListProducts.add(new WaitingListProduct(item, amount));
                    }
                }
            }
        }
        WaitingListProductsDB.updateWaitingListProducts(waitingListProducts);
    }*/


    private void parseWaitingList(Map<String, Object> requestBody) {
    waitingListProducts = new ArrayList<>();
    List<Map<String, Object>> waitingListWorkstations = (List<Map<String, Object>>) requestBody.get("waitinglistworkstations");
    Map<Integer, Integer> itemWaitlistQuantityMap = new HashMap<>();

    for (Map<String, Object> workstation : waitingListWorkstations) {
        List<Map<String, Object>> waitingLists = (List<Map<String, Object>>) workstation.get("waitinglist");
        if (waitingLists != null) {
            for (Map<String, Object> waitingList : waitingLists) {
                Integer item = Integer.parseInt(waitingList.get("item").toString());
                Integer waitlistQuantity = Integer.parseInt(waitingList.get("amount").toString());

                if (itemWaitlistQuantityMap.containsKey(item)) {
                    continue;
                }
                itemWaitlistQuantityMap.put(item, waitlistQuantity);
            }
        }
    }

    for (Map.Entry<Integer, Integer> entry : itemWaitlistQuantityMap.entrySet()) {
        Integer item = entry.getKey();
        Integer waitlistQuantity = entry.getValue();

        waitingListProducts.add(new WaitingListProduct(item, waitlistQuantity, 0));
    }

    List<Map<String, Object>> ordersInWork = (List<Map<String, Object>>) requestBody.get("ordersinwork");
    for (Map<String, Object> orderInWork : ordersInWork) {
        Integer item = Integer.parseInt(orderInWork.get("item").toString());
        Integer inworkQuantity = Integer.parseInt(orderInWork.get("amount").toString());

        WaitingListProduct existingProduct = findWaitingListProduct(waitingListProducts, item);
        if (existingProduct != null) {
            existingProduct.setInWorkQuantity(inworkQuantity);
        } else {
            waitingListProducts.add(new WaitingListProduct(item, 0, inworkQuantity));
        }
    }
    WaitingListProductsDB.putWaitingListProducts(waitingListProducts);
    }

    private WaitingListProduct findWaitingListProduct(List<WaitingListProduct> productList, Integer item) {
        for (WaitingListProduct product : productList) {
            if (product.getProductId() == item) {
                return product;
            }
        }
        return null;
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
        ArrayList<Order> ordersDb = new ArrayList<Order>();
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
        System.out.println(" *** Warteliste der jeweiligen Arbeitsplätze *** ");
        System.out.println();
        for (Map.Entry<Integer, Integer> entry : workstations.entrySet()) {
            System.out.println("Produktnummer: " + entry.getKey() + ": " + entry.getValue() + " Stück");
        }
        System.out.println();
        System.out.println(" *** Ermittlung der Produkte die sich in der Warteschlange befinden *** ");
        System.out.println();
        for (WaitingListProduct waitingListProduct : waitingListProducts) {
            if (waitingListProduct.getWaitlistQuantity() != 0) {
                System.out.println("Produktnummer: " + waitingListProduct.getProductId() + " - " + waitingListProduct.getWaitlistQuantity() + " Stück");
            }
        }
        System.out.println();
        System.out.println(" *** Ermittlung der Produkte die sich in Bearbeitung befinden *** ");
        System.out.println();
        for (WaitingListProduct waitingListProduct : waitingListProducts) {
            if (waitingListProduct.getInWorkQuantity() != 0) {
                System.out.println("Produktnummer: " + waitingListProduct.getProductId() + " - " + waitingListProduct.getInWorkQuantity() + " Stück");
            }
        }
    }


}
