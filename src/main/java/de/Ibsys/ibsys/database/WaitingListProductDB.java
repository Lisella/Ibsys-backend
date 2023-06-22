package de.Ibsys.ibsys.database;

import java.util.ArrayList;

import de.Ibsys.ibsys.Production.WaitingListProducts;

public class WaitingListProductDB {
    // Wie viele von welchem Produkt. Maschine egal.
    public static ArrayList<WaitingListProducts> GetWaitingListProductsFromDB() {
        ArrayList<WaitingListProducts> waitingListProducts = new ArrayList<>();

        // todo get from db

        waitingListProducts.add(new WaitingListProducts(1, 10));
        return waitingListProducts;
    }
}
