import java.time.LocalDate;
import java.util.HashMap;

public class Product {
    int id;
    String name;
    int deliverytime;
    double variance;
    int product1Consumption;
    int product2Consumption;
    int product3Consumption;
    int discountQuantity;
    HashMap<LocalDate, Integer> stockHistory;

    // create constructor
    public Product(int id, String name, int discountQuantity, int deliverytime, double variance,
            int product1Consumption, int product2Consumption, int product3Consumption, int stock,
            LocalDate currentStockDate) {
        this.id = id;
        this.name = name;
        this.discountQuantity = discountQuantity;
        this.deliverytime = deliverytime;
        this.variance = variance;
        this.product1Consumption = product1Consumption;
        this.product2Consumption = product2Consumption;
        this.product3Consumption = product3Consumption;
        this.stockHistory = new HashMap<LocalDate, Integer>();
        this.stockHistory.put(currentStockDate, stock);
        // add to the stock history entries for the next four weeks without sundays as
        // keys
        // and the stock as value
        for (int i = 0; i < 28; i++) {
            LocalDate date = currentStockDate.plusDays(i);
            if (date.getDayOfWeek().getValue() != 7 && date.getDayOfWeek().getValue() != 6) {
                this.stockHistory.put(date, stock);
            }
        }
    }
}
