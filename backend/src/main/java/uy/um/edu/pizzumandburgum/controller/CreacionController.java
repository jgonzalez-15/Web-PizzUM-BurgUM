package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.response.CreacionResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.CreacionService;

@RestController
@RequestMapping("/api/creacion")
@CrossOrigin(origins = "http://localhost:5173")
public class CreacionController {

    @Autowired
    private CreacionService creacionService;

    @GetMapping("/{id}/listar")
    public ResponseEntity<CreacionResponseDTO> obtenerCreacion(@PathVariable Long id) {
        CreacionResponseDTO creacion = creacionService.obtenerCreacionPorId(id);
        return ResponseEntity.ok(creacion);
    }
}