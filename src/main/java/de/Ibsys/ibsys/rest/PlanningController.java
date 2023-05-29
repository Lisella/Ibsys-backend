package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.Ordering.ProductionPlanEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/planning")
public class PlanningController {

    @PostMapping
    public ResponseEntity<String> processPlanning(@RequestBody ArrayList<ProductionPlanEntity> planningList) {
        // Hier kannst du die empfangene Liste verarbeiten
        // Zum Beispiel:
        for (ProductionPlanEntity planEntity : planningList) {
            System.out.println("Periode: " + planEntity.periode);
            System.out.println("Product1 Consumption: " + planEntity.product1Consumption);
            System.out.println("Product2 Consumption: " + planEntity.product2Consumption);
            System.out.println("Product3 Consumption: " + planEntity.product3Consumption);
        }

        // Rückgabe einer Erfolgsmeldung
        return new ResponseEntity<>("Planning successfully processed", HttpStatus.OK);
    }
}
