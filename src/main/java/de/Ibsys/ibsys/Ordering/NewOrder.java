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
}
