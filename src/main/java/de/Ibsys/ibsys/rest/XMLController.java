package de.Ibsys.ibsys.rest;

import de.Ibsys.ibsys.entity.Input;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class XMLController {

    @PostMapping("/in")
    public ResponseEntity<String> processInput(@RequestBody Input input) {



        return ResponseEntity.ok("Erfolgreich verarbeitet");
    }

    @GetMapping("/input")
    public ResponseEntity<String> getResponse() {



        return ResponseEntity.ok("ok");
    }
}

