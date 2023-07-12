package de.Ibsys.ibsys.rest;

import java.util.*;

import de.Ibsys.ibsys.Production.ProductionProduct;
import de.Ibsys.ibsys.Production.WaitingListProduct;
import de.Ibsys.ibsys.database.ProductionProductsDB;
import de.Ibsys.ibsys.database.WaitingListProductsDB;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.Ibsys.ibsys.Ordering.Product;
import de.Ibsys.ibsys.database.ForecastsDB;
import de.Ibsys.ibsys.database.ProductsDB;

@RestController
@RequestMapping("/api")
public class ForecastsController {

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/forecasts")
    public ResponseEntity<ForecastResponse> getForecast() {
        ArrayList<de.Ibsys.ibsys.InputXml.Item> forecast = ForecastsDB.getForecast();
        ArrayList<Product> products = ProductsDB.getProducts();
        ArrayList<ProductionProduct> productionProducts = ProductionProductsDB.getProductionProducts();
        ArrayList<WaitingListProduct> waitingListProducts = WaitingListProductsDB.getWaitingListProductsFromDB();

        ArrayList<ProductInfo> forP1 = new ArrayList<>();
        ArrayList<ProductInfo> forP2 = new ArrayList<>();
        ArrayList<ProductInfo> forP3 = new ArrayList<>();

        for (ProductionProduct productionProduct : productionProducts) {
            int productId = productionProduct.getId();
            int product1Consumption = productionProduct.getProduct1Consumption();
            int product2Consumption = productionProduct.getProduct2Consumption();
            int product3Consumption = productionProduct.getProduct3Consumption();
            int stock = productionProduct.getStock();
            String name = productionProduct.getName();
            int ordersInWorkQuantity = getOrdersInWorkProductById(productId, waitingListProducts);
            int waitingListQuantity = getWaitingListProductById(productId, waitingListProducts);

            if (product1Consumption > 0) {
                ProductInfo productInfo = new ProductInfo(productId, name, stock, waitingListQuantity, ordersInWorkQuantity);
                forP1.add(productInfo);
            }
            if (product2Consumption > 0) {
                ProductInfo productInfo = new ProductInfo(productId, name, stock, waitingListQuantity, ordersInWorkQuantity);
                forP2.add(productInfo);
            }
            if (product3Consumption > 0) {
                ProductInfo productInfo = new ProductInfo(productId, name, stock, waitingListQuantity, ordersInWorkQuantity);
                forP3.add(productInfo);
            }
        }

        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        for (Product product : products) {
            int productId = product.getId();
            int stock = product.getStock();
            String name = product.getName();
            int waitingListQuantity = getWaitingListProductById(productId, waitingListProducts);
            int ordersInWorkQuantity = getOrdersInWorkProductById(productId, waitingListProducts);
            productInfos.add(new ProductInfo(productId, name, stock, waitingListQuantity, ordersInWorkQuantity));
        }

        ForecastResponse response = new ForecastResponse();
        response.setForecasts(forecast);
        response.setP1(convertToMapList(forP1));
        response.setP2(convertToMapList(forP2));
        response.setP3(convertToMapList(forP3));

        return ResponseEntity.ok(response);
    }

    public List<Map<String, Object>> convertToMapList(ArrayList<ProductInfo> productInfos) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ProductInfo productInfo : productInfos) {
            Map<String, Object> map = new LinkedHashMap<>();  // Verwenden Sie LinkedHashMap, um die Reihenfolge der Felder beizubehalten
            map.put("productId", productInfo.getProductId());
            map.put("name", productInfo.getName());
            map.put("stock", productInfo.getStock());
            map.put("waitingListQuantity", productInfo.getWaitingListQuantity());
            map.put("ordersInWorkQuantity", productInfo.getOrdersInWorkQuantity());
            mapList.add(map);
        }
        return mapList;
    }

    public class ForecastResponse {
        private ArrayList<de.Ibsys.ibsys.InputXml.Item> forecasts;
        private List<Map<String, Object>> p1;
        private List<Map<String, Object>> p2;
        private List<Map<String, Object>> p3;

        public ArrayList<de.Ibsys.ibsys.InputXml.Item> getForecasts() {
            return forecasts;
        }

        public void setForecasts(ArrayList<de.Ibsys.ibsys.InputXml.Item> forecasts) {
            this.forecasts = forecasts;
        }

        public List<Map<String, Object>> getP1() {
            return p1;
        }

        public void setP1(List<Map<String, Object>> p1) {
            this.p1 = p1;
        }

        public List<Map<String, Object>> getP2() {
            return p2;
        }

        public void setP2(List<Map<String, Object>> p2) {
            this.p2 = p2;
        }

        public List<Map<String, Object>> getP3() {
            return p3;
        }

        public void setP3(List<Map<String, Object>> p3) {
            this.p3 = p3;
        }
    }

    public class ProductInfo {
        private int productId;
        private String name;
        private int stock;
        private int waitingListQuantity;
        private int ordersInWorkQuantity;

        public ProductInfo(int productId, String name, int stock, int waitingListQuantity, int ordersInWorkQuantity) {
            this.productId = productId;
            this.name = name;
            this.stock = stock;
            this.waitingListQuantity = waitingListQuantity;
            this.ordersInWorkQuantity = ordersInWorkQuantity;
        }


        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getWaitingListQuantity() {
            return waitingListQuantity;
        }

        public void setWaitingListQuantity(int waitingListQuantity) {
            this.waitingListQuantity = waitingListQuantity;
        }

        public int getOrdersInWorkQuantity() {
            return ordersInWorkQuantity;
        }

        public void setOrdersInWorkQuantity(int ordersInWorkQuantity) {
            this.ordersInWorkQuantity = ordersInWorkQuantity;
        }
    }

    public static int getWaitingListProductById(int productId, ArrayList<WaitingListProduct> waitinglistProducts) {
        for (WaitingListProduct waitingListProduct : waitinglistProducts) {
            if (waitingListProduct.getProductId() == productId) {
                return waitingListProduct.getWaitlistQuantity();
            }
        }
        return 0;
    }

    public static int getOrdersInWorkProductById(int productId, ArrayList<WaitingListProduct> waitingListProducts) {
        for (WaitingListProduct waitingListProduct : waitingListProducts) {
            if (waitingListProduct.getProductId() == productId) {
                return waitingListProduct.getInWorkQuantity();
            }
        }
        return 0;

    }

}
