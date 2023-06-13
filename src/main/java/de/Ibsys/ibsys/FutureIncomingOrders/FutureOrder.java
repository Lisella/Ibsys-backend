package de.Ibsys.ibsys.FutureIncomingOrders;

import java.util.ArrayList;
import java.util.HashMap;

public class FutureOrder {
    int productId;
    int quantity;
    int daysAfterToday;

    // create consutructor
    public FutureOrder(int productId, int quantity, int daysAfterToday) {
        this.productId = productId;
        this.quantity = quantity;
        this.daysAfterToday = daysAfterToday;
    }

    public int getProductId() {
        return this.productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getDaysAfterToday() {
        return this.daysAfterToday;
    }

    // write setters
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDaysAfterToday(int daysAfterToday) {
        this.daysAfterToday = daysAfterToday;
    }
}