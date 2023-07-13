package de.Ibsys.ibsys.Production;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductionItem {
    public int id;
    private int article;
    private int quantity;
    private int sequenceNumer;
    private String Name;

    public ProductionItem(int article, String name, int quantity) {
        this.article = article;
        this.quantity = quantity;
        this.Name = name;
    }

    // Standardkonstruktor, Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumer = sequenceNumber;
    }

    public int getSequenceNumer() {
        return sequenceNumer;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
