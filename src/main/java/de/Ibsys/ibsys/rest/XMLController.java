package de.Ibsys.ibsys.rest;

<<<<<<< Updated upstream
import de.Ibsys.ibsys.OutputXml.WarehouseStock;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
=======
>>>>>>> Stashed changes
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;

@RestController
@RequestMapping("/api")
public class XMLController {
<<<<<<< Updated upstream
    @PostMapping("/in")
    public ResponseEntity<String> processInput(@RequestBody String inputXml) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(WarehouseStock.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        WarehouseStock warehouse = (WarehouseStock) unmarshaller.unmarshal(new StringReader(inputXml));
        warehouse.update(warehouse);
        // Erfolgreiche Verarbeitung
        return ResponseEntity.ok("Input processed successfully");

    }
=======
        @PostMapping("/in")
        public ResponseEntity processInput(@RequestBody String inputXml) {
            try {
                return ResponseEntity.ok(inputXml);
            } catch (Exception e) {
                // Hier können Sie den Umgang mit Fehlern implementieren, falls erforderlich.
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein Fehler ist aufgetreten.");
            }
        }
>>>>>>> Stashed changes

    @GetMapping("/input")
    public ResponseEntity<String> getResponse() {



        return ResponseEntity.ok("ok");
    }
}

