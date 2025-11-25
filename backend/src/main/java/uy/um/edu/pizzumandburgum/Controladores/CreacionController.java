package uy.um.edu.pizzumandburgum.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.DTOs.Response.CreacionResponseDTO;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.CreacionService;

@RestController
@RequestMapping("/api/creacion")
@PreAuthorize("hasAuthority('CLIENTE') or hasAnyAuthority('ADMIN')")
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