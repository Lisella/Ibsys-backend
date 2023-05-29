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

    public static NewOrder createOrders(Product product) {
        int quantity = 0;
        // iterate over product.StockHistory and create orders if necessary
        for (int i = 0; i < 28 - product.deliverytime - product.variance; i++) {
            if (product.stockHistory.get(i + product.deliverytime + product.variance) < 0) {
                if (i < 7)
                    quantity += product.discountQuantity;
                for (int j = i + product.deliverytime + product.variance; j < 28; j++) {
                    product.stockHistory.put(j, product.stockHistory.get(j) + product.discountQuantity);
                }
            }
        }
        return new NewOrder(product.id, quantity, 5);
    }
}
