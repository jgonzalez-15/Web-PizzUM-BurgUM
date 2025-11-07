package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.CalificacionRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.CalificacionResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.CalificacionService;

@RestController
@RequestMapping("/api/calificacion")
@CrossOrigin(origins = "http://localhost:5173")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @PreAuthorize("hasAuthority('CLIENTE')")
    @PostMapping("/agregar")
    public ResponseEntity<CalificacionResponseDTO> crear(@RequestBody CalificacionRequestDTO dto, HttpSession sesion) {
        CalificacionResponseDTO calificacion = calificacionService.crearCalificacion(dto);
        return ResponseEntity.ok(calificacion);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/pedido/{id}")
    public CalificacionResponseDTO listarPorPedido(@PathVariable Long id) {
        return calificacionService.calificacionPorPedido(id);
    }
}
