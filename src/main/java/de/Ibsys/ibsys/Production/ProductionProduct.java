package de.Ibsys.ibsys.Production;

public class ProductionProduct {
    int id;
    String name;
    int product1Consumption;
    int product2Consumption;
    int product3Consumption;
    int stock;

    public ProductionProduct(int Id, String Name, int p1, int p2, int p3, int stock) {
        this.id = Id;
        this.name = Name;
        this.product1Consumption = p1;
        this.product2Consumption = p2;
        this.product3Consumption = p3;
        this.stock = stock;
    }

    // { 22, Rahmen, 1, 0, 0, 13 },
}
