package de.Ibsys.ibsys.OutputXml;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class Article {
    private int id;
    private int amount;
    private int startAmount;
    private double percentage;
    private double price;
    private double stockValue;

    public Article() {
    }

    public Article(int id, int amount, int startAmount, double percentage, double price, double stockValue) {
        this.id = id;
        this.amount = amount;
        this.startAmount = startAmount;
        this.percentage = percentage;
        this.price = price;
        this.stockValue = stockValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }

}


