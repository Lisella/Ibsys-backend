package de.Ibsys.ibsys.rest;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.Ibsys.ibsys.InputXml.Item;
import de.Ibsys.ibsys.database.ForecastsDB;

@RestController
@RequestMapping("/api")
public class ForecatsController {
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/forecast")
    public ResponseEntity<ArrayList<Item>> getForecast() {
        ArrayList<Item> forecast = ForecastsDB.getForecast();
        return ResponseEntity.ok(forecast);
    }
}
