package de.Ibsys.ibsys.Production;

import java.util.ArrayList;

public class WaitingListProduct {
    private int productId;
    private int quantity;

    //TODO: Ändern zu in productID, int waitlistQuantity, int inWorkQuantity
    public WaitingListProduct(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    //public int getInWorkQuantity() {
    //        return inWorkQuantity;
    //    }

    // public int getWaitlistQuantity() {
    //        return waitlistQuantity;
    //    }
    public int getQuantity() {
        return quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    //public int setInWorkQuantity(int inWorkQuantity) {
    //        this.inWorkQuantity = inWorkQuantity;
    //    }

    // public int setWaitlistQuantity(int waitListQuantity) {
    //        this.waitlistQuantity = waitlistQuantity;
    //    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // Das ist doch GetWaitingListProductsFromDB() oder nicht?
    public static int GetWaitingListQuantity(int productId, ArrayList<WaitingListProduct> waitingListProducts) {

        for (WaitingListProduct waitingListProduct : waitingListProducts) {
            if (waitingListProduct.getProductId() == productId) {
                return waitingListProduct.getQuantity();
            }
        }
        return 0;

    }
}
