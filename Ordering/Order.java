package Ordering;

import java.time.LocalDate;

public class Order {
    int id;
    int productId;
    int quantity;
    LocalDate date;

    // create consutructor
    public Order(int id, int productId, int quantity, LocalDate date) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.date = date;
    }
}
