package de.Ibsys.ibsys.Ordering;

import java.util.ArrayList;

public class Main {

    public static ArrayList<NewOrder> createOrders() {

        ArrayList<NewOrder> orders = new ArrayList<>();

        // get Forecast
        ArrayList<ProductionPlanEntity> productionPlanEntity = ProductionPlanEntity.getForecast();
        // log forecast

        // get Products
        ArrayList<Product> products = Product.getProducts();
        // log stockHistories

        // update stockHistories foreach product by open orders and production needs
        for (Product product : products) {
            // update stockHistorieByOrders
            product.stockHistory = Order.updateStockHistoryByOrders(product);

            // update stockHistoryByForecast
            product.stockHistory = Product.updateStockHistoryByForecast(product, productionPlanEntity);

            // create orders for the product
            //orders.add(NewOrder.createOrder(product));
        }
        return orders;
    }

    public static void main(String[] args) {
        System.out.println(createOrders());
    }


}
