package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/agregar")
    public ResponseEntity<CalificacionResponseDTO> crear(@RequestBody CalificacionRequestDTO dto) {
        CalificacionResponseDTO calificacion = calificacionService.crearCalificacion(dto);
        return ResponseEntity.ok(calificacion);
    }

    @GetMapping("/pedido/{id}")
    public CalificacionResponseDTO listarPorPedido(@PathVariable Long id) {
        return calificacionService.calificacionPorPedido(id);
    }
}