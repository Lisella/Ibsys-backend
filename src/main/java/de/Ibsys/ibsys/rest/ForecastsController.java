package de.Ibsys.ibsys.rest;

import java.util.ArrayList;

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

        // Get ProductionProducts, sortiere p1,p2,p3 Consumption
        ArrayList<ProductInfo> forP1 = new ArrayList<>();
        ArrayList<ProductInfo> forP2 = new ArrayList<>();
        ArrayList<ProductInfo> forP3 = new ArrayList<>();

        // Ausgabe der Produktinformationen
        for (Product product : products) {
            System.out.println("ProductId: " + product.getId());
            System.out.println("Stock: " + product.getStock());
        }

        // TODO: ProdutionProducts aus DB holen
        // TODO: ProductionProducts in ProductInfo mappen
        // TODO Je Produkt die benötigten Production Products ausgeben

        ArrayList<ProductInfo> productInfos = new ArrayList<>();
        for (Product product : products) {
            System.out.println("ProductId: " + product.getId() + " Stock: " + product.getStock());
            productInfos.add(new ProductInfo(product.getId(), product.getStock()));
        }


        // mocke p1, p2 p3
        productInfos.add(new ProductInfo(1, 70));
        productInfos.add(new ProductInfo(2, 40));
        productInfos.add(new ProductInfo(3, 40));

        // Consumption hinzufügen, danach nach p1,p2,p3 sortieren

        ForecastResponse response = new ForecastResponse();
        response.setForecasts(forecast);
        response.setProducts(productInfos);



        return ResponseEntity.ok(response);
    }

    public class ForecastResponse {
        private ArrayList<de.Ibsys.ibsys.InputXml.Item> forecasts;
        private ArrayList<ProductInfo> products;

        // Getter und Setter

        public ArrayList<de.Ibsys.ibsys.InputXml.Item> getForecasts() {
            return forecasts;
        }

        public void setForecasts(ArrayList<de.Ibsys.ibsys.InputXml.Item> forecast) {
            this.forecasts = forecast;
        }

        public ArrayList<ProductInfo> getProducts() {
            return products;
        }

        public void setProducts(ArrayList<ProductInfo> products) {
            this.products = products;
        }
    }

    public class Item {
        private int periode;
        private int p1;
        private int p2;
        private int p3;

        // Getter und Setter

        public int getPeriode() {
            return periode;
        }

        public void setPeriode(int periode) {
            this.periode = periode;
        }

        public int getP1() {
            return p1;
        }

        public void setP1(int p1) {
            this.p1 = p1;
        }

        public int getP2() {
            return p2;
        }

        public void setP2(int p2) {
            this.p2 = p2;
        }

        public int getP3() {
            return p3;
        }

        public void setP3(int p3) {
            this.p3 = p3;
        }
    }

    public class ProductInfo {
        private int productId;
        private int stock;

        // Getter und Setter

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