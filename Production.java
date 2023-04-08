import java.util.ArrayList;

public class Production {
    int productId;
    int quantity;
    ArrayList<ProductWorkplace> workplaces;

    // create constructor
    public Production(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        workplaces = getWorkplaces(productId);
    }

    public ArrayList<ProductWorkplace> getWorkplaces(int productId) {
        ArrayList<ProductWorkplace> result = new ArrayList<>();
        // code to retrieve ProductWorkplace objects that match productId and add them
        // to result ArrayList
        return result;
    }
}
