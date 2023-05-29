package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.OutputXml.WarehouseStock;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;

@RestController
@RequestMapping("/api")
public class XMLController {
    @PostMapping("/in")
    public ResponseEntity<String> processInput(@RequestBody String inputXml) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(WarehouseStock.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        WarehouseStock warehouse = (WarehouseStock) unmarshaller.unmarshal(new StringReader(inputXml));
        warehouse.update(warehouse);
        // Erfolgreiche Verarbeitung
        return ResponseEntity.ok("Input processed successfully");

    }

    @GetMapping("/input")
    public ResponseEntity<String> getResponse() {



        return ResponseEntity.ok("ok");
    }
}

