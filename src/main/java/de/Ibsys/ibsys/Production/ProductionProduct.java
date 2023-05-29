package de.Ibsys.ibsys.Production;

import java.util.ArrayList;

public class ProductionProduct {
    int id;
    String name;
    int product1Consumption;
    int product2Consumption;
    int product3Consumption;

    ProductionProduct(int Id, String Name, int p1, int p2, int p3){

        this.id = Id;
        this.name = Name;
        this.product1Consumption = p1;
        this.product2Consumption = p2;
        this.product3Consumption = p3;
    }
    public static ArrayList<ProductionProduct> getProductionProductsFromDB(){
        ArrayList<ProductionProduct> products = new ArrayList<>();

        ProductionProduct product = new ProductionProduct(1, "Test", 150, 150, 200);
        products.add(product);
        return products;
    }
}
