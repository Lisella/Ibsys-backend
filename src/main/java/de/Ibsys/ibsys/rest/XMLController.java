package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.entity.Input;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class XMLController {

    @PostMapping("/input")
    public ResponseEntity<String> processInput(@RequestBody Input input) {
        // Verarbeiten Sie die Input-Daten und führen Sie die entsprechenden Operationen durch
        // ...

        return ResponseEntity.ok("Erfolgreich verarbeitet");
    }
}

