package Ordering;

import java.util.ArrayList;

public class Forecast {
    int periode;
    int product1Consumption;
    int product2Consumption;
    int product3Consumption;

    // constructor
    public Forecast(int periode, int product1Consumption, int product2Consumption, int product3Consumption) {
        this.periode = periode;
        this.product1Consumption = product1Consumption;
        this.product2Consumption = product2Consumption;
        this.product3Consumption = product3Consumption;
    }

    // create method getForecast
    public static ArrayList<Forecast> getForecast() {
        ArrayList<Forecast> forecasts = new ArrayList<Forecast>();
        forecasts.add(new Forecast(1, 200, 150, 100));
        forecasts.add(new Forecast(2, 200, 150, 100));
        forecasts.add(new Forecast(3, 250, 150, 250));
        forecasts.add(new Forecast(4, 250, 150, 150));
        return forecasts;
    }

    public static ArrayList<Integer> calcNeedsForWeek(Product product, ArrayList<Forecast> forecast) {
        ArrayList<Integer> needs = new ArrayList<>();
        for (Forecast f : forecast) {
            needs.add(f.product1Consumption * product.product1Consumption
                    + f.product2Consumption * product.product2Consumption
                    + f.product3Consumption * product.product3Consumption);
        }
        return needs;
    }
}