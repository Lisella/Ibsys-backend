package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.entity.Input;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class XMLController {
        @PostMapping("/in")
        public ResponseEntity<String> processInput(@RequestBody String inputXml) {
            try {
                return ResponseEntity.ok(inputXml);
            } catch (Exception e) {
                // Hier können Sie den Umgang mit Fehlern implementieren, falls erforderlich.
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein Fehler ist aufgetreten.");
            }
        }

    @GetMapping("/input")
    public ResponseEntity<String> getResponse() {



        return ResponseEntity.ok("ok");
    }
}

