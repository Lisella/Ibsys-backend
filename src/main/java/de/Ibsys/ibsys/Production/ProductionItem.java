package de.Ibsys.ibsys.Production;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductionItem {
    private int article;
    private int quantity;

    public ProductionItem(int id, int quantity) {
        this.article = id;
        this.quantity = quantity;
    }

    // Standardkonstruktor, Getter und Setter

    @JsonProperty("article")
    public int getArticle() {
        return article;
    }

    @JsonProperty("article")
    public void setArticle(int article) {
        this.article = article;
    }

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return article;
    }
}
