package de.Ibsys.ibsys.OutputXml;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.util.HashMap;

@XmlRootElement(name = "article")
public class WarehouseStock {
    private int id;
    private int amount;
    private int startAmount;
    private double percentage;
    private double price;
    private double stockValue;

    public WarehouseStock() {
    }

    public WarehouseStock(int id, int amount, int startAmount, double percentage, double price, double stockValue) {
        this.id = id;
        this.amount = amount;
        this.startAmount = startAmount;
        this.percentage = percentage;
        this.price = price;
        this.stockValue = stockValue;
    }

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @XmlAttribute(name = "startamount")
    public int getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
    }

    @XmlAttribute(name = "pct")
    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @XmlAttribute(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @XmlAttribute(name = "stockvalue")
    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }


    public HashMap<Integer, Integer> update(WarehouseStock warehouseList) {
        HashMap<Integer, Integer> warehouseStock = new HashMap<>();
        warehouseStock.put(warehouseList.getId(), warehouseList.getAmount());
        return warehouseStock;

        }
    }
