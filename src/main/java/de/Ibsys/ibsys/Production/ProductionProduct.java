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

    //Write getter for productConsumptions
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getProduct1Consumption() {
        return product1Consumption;
    }

    public int getProduct2Consumption() {
        return product2Consumption;
    }

    public int getProduct3Consumption() {
        return product3Consumption;
    }

    public int getStock() {
        return stock;
    }

    // { 22, Rahmen, 1, 0, 0, 13 },
}
