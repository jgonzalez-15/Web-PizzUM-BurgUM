package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.service.HamburguesaService;

@RestController
@RequestMapping("/api/hamburguesa")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class HamburguesaController {
    @Autowired
    private HamburguesaService hamburguesaService;

    @PostMapping
    public ResponseEntity<HamburguesaResponseDTO> crearHamburguesa(@RequestBody HamburguesaResponseDTO hamburguesa) {
        HamburguesaResponseDTO nueva = hamburguesaService.crearHamburguesa(hamburguesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
}
