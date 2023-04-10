package Ordering;

public class NewOrder {
    int article;
    int quantity;
    int modus;

    public NewOrder(int article, int quantity, int modus) {
        this.article = article;
        this.quantity = quantity;
        this.modus = modus;
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

    // override toString method
    @Override
    public String toString() {
        return "NewOrder [article=" + article + ", quantity=" + quantity + ", modus=" + modus + "]";
    }
}
