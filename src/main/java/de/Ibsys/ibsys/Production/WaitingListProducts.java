package de.Ibsys.ibsys.Production;

import java.util.ArrayList;

public class WaitingListProducts {
    private int productId;
    private int quantity;

    public WaitingListProducts(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static int GetWaitingListQuantity(int productId, ArrayList<WaitingListProducts> waitingListProducts) {

        for (WaitingListProducts waitingListProduct : waitingListProducts) {
            if (waitingListProduct.getProductId() == productId) {
                return waitingListProduct.getQuantity();
            }
        }
        return 0;

    }
}
