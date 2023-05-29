package de.Ibsys.ibsys.Ordering;

import java.util.ArrayList;

public class ProductionPlanEntity {
    public int periode;
    public int product1Consumption;
    public int product2Consumption;
    public int product3Consumption;

    // constructor
    public ProductionPlanEntity(int periode, int product1Consumption, int product2Consumption, int product3Consumption) {
        this.periode = periode;
        this.product1Consumption = product1Consumption;
        this.product2Consumption = product2Consumption;
        this.product3Consumption = product3Consumption;
    }

    // create method getForecast
    public static ArrayList<ProductionPlanEntity> getForecast() {
        ArrayList<ProductionPlanEntity> productionPlanEntities = new ArrayList<ProductionPlanEntity>();
        productionPlanEntities.add(new ProductionPlanEntity(1, 200, 150, 100));
        productionPlanEntities.add(new ProductionPlanEntity(2, 200, 150, 100));
        productionPlanEntities.add(new ProductionPlanEntity(3, 250, 150, 250));
        productionPlanEntities.add(new ProductionPlanEntity(4, 250, 150, 150));
        return productionPlanEntities;
    }

    public static ArrayList<Integer> calcNeedsForWeek(Product product, ArrayList<ProductionPlanEntity> productionPlanEntity) {
        ArrayList<Integer> needs = new ArrayList<>();
        for (ProductionPlanEntity f : productionPlanEntity) {
            needs.add(f.product1Consumption * product.product1Consumption
                    + f.product2Consumption * product.product2Consumption
                    + f.product3Consumption * product.product3Consumption);
        }
        return needs;
    }
}