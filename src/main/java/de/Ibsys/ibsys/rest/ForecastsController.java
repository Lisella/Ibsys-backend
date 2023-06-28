package de.Ibsys.ibsys.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.Ibsys.ibsys.Production.ProductionProduct;
import de.Ibsys.ibsys.database.ProductionProductsDB;
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

        ArrayList<ProductInfo> forP1 = new ArrayList<>();
        ArrayList<ProductInfo> forP2 = new ArrayList<>();
        ArrayList<ProductInfo> forP3 = new ArrayList<>();

        for (ProductionProduct productionProduct : productionProducts) {
            int productId = productionProduct.getId();
            int product1Consumption = productionProduct.getProduct1Consumption();
            int product2Consumption = productionProduct.getProduct2Consumption();
            int product3Consumption = productionProduct.getProduct3Consumption();

            if (product1Consumption > 0) {
                ProductInfo productInfo = new ProductInfo(productId, product1Consumption);
                forP1.add(productInfo);
            }
            if (product2Consumption > 0) {
                ProductInfo productInfo = new ProductInfo(productId, product2Consumption);
                forP2.add(productInfo);
            }
            if (product3Consumption > 0) {
                ProductInfo productInfo = new ProductInfo(productId, product3Consumption);
                forP3.add(productInfo);
            }
        }

        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        for (Product product : products) {
            int productId = product.getId();
            int stock = product.getStock();
            productInfos.add(new ProductInfo(productId, stock));
        }

        // mocke p1, p2, p3
        productInfos.add(new ProductInfo(1, 70));
        productInfos.add(new ProductInfo(2, 40));
        productInfos.add(new ProductInfo(3, 40));

        ForecastResponse response = new ForecastResponse();
        response.setForecasts(forecast);
        response.setP1(convertToMapList(forP1));
        response.setP2(convertToMapList(forP2));
        response.setP3(convertToMapList(forP3));
        response.setProducts(productInfos);

        // Konsolenausgabe für Produktinformationen
        System.out.println("=== Produktinformationen ===");
        for (ProductInfo productInfo : productInfos) {
            System.out.println("ProductId: " + productInfo.getProductId() + " Stock: " + productInfo.getStock());
        }

        return ResponseEntity.ok(response);
    }

    public List<Map<String, Object>> convertToMapList(ArrayList<ProductInfo> productInfos) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ProductInfo productInfo : productInfos) {
            Map<String, Object> map = new HashMap<>();
            map.put("productId", productInfo.getProductId());
            map.put("stock", productInfo.getStock());
            mapList.add(map);
        }
        return mapList;
    }

    public class ForecastResponse {
        private ArrayList<de.Ibsys.ibsys.InputXml.Item> forecasts;
        private ArrayList<ProductInfo> products;
        private List<Map<String, Object>> p1;
        private List<Map<String, Object>> p2;
        private List<Map<String, Object>> p3;

        public ArrayList<de.Ibsys.ibsys.InputXml.Item> getForecasts() {
            return forecasts;
        }

        public void setForecasts(ArrayList<de.Ibsys.ibsys.InputXml.Item> forecasts) {
            this.forecasts = forecasts;
        }

        public ArrayList<ProductInfo> getProducts() {
            return products;
        }

        public void setProducts(ArrayList<ProductInfo> products) {
            this.products = products;
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
        private int stock;

        public ProductInfo(int productId, int stock) {
            this.productId = productId;
            this.stock = stock;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}
