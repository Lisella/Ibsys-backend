package de.Ibsys.ibsys.rest;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.Ibsys.ibsys.Production.ProductionItem;
import de.Ibsys.ibsys.WorkingTimes.WorkingTime;

@RestController
@RequestMapping("/api")
public class CapacityController {

    @CrossOrigin(origins = "http://localhost:5173")

    @PostMapping("/capacity")
    public ArrayList<WorkingTime> updateCapacity(@RequestBody ProductionItem[] items) {
        ArrayList<ProductionItem> productionItems = new ArrayList<>(Arrays.asList(items));

        // Hier kannst du die ArrayList<ProductionItem> weiterverarbeiten
        // ...

        // Beispiel: Ausgabe der empfangenen Daten
        for (ProductionItem item : productionItems) {
            System.out.println("Sequence Number: " + item.getSequenceNumer());
            System.out.println("Article: " + item.getArticle());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println();
        }

        System.out.println(("Kapazitätsplanung Berechnung gestartet"));
        ArrayList<de.Ibsys.ibsys.WorkingTimes.WorkingTime> workingTimes = de.Ibsys.ibsys.WorkingTimes.Calculations
                .CalculateWorkingtimesByProductionList(productionItems);

        System.out.println("Kapazitätsplanung Berechnung abgeschlossen:");
        System.out.println("----------------------");

        return workingTimes;
    }
}