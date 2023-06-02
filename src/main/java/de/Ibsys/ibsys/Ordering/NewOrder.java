package de.Ibsys.ibsys.Ordering;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewOrder {
    private int article;
    private int quantity;
    private int modus;

    @JsonCreator
    public NewOrder(@JsonProperty("article") int article,
                    @JsonProperty("quantity") int quantity,
                    @JsonProperty("modus") int modus) {
        this.article = article;
        this.quantity = quantity;
        this.modus = modus;
    }

    // Getters and setters

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getModus() {
        return modus;
    }

    public void setModus(int modus) {
        this.modus = modus;
    }

    public static NewOrder createOrder(Product product) {
        int quantity = 0;
        // iterate over product.StockHistory and create orders if necessary
        for (int i = 0; i < 28 - product.deliverytime - product.variance; i++) {
            if (product.stockHistory.get(i + product.deliverytime + product.variance) < 0) {
               //todo  why < 7
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
