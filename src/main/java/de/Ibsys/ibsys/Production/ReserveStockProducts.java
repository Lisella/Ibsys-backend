package de.Ibsys.ibsys.Production;

import java.util.ArrayList;

public class ReserveStockProducts {
    public int productId;
    public int reserveStock;

    public ReserveStockProducts(int productId, int reserveStock) {
        this.productId = productId;
        this.reserveStock = reserveStock;
    }

    public int getProductId() {
        return productId;
    }

    public int getReserveStock() {
        return reserveStock;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setReserveStock(int reserveStock) {
        this.reserveStock = reserveStock;
    }

    public static int GetReserveStock(int productId, ArrayList<ReserveStockProducts> reserveStocks) {

        for (ReserveStockProducts reserveStock : reserveStocks) {
            if (reserveStock.getProductId() == productId) {
                return reserveStock.getReserveStock();
            }
        }
        return 0;
    }
}
