package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ReporteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:5173")

public class RepositoryController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/tarjetas")
    public ResponseEntity<List<MedioDePagoDTO>> obtenerTarjetas() {
        return ResponseEntity.ok(reporteService.obtenerDatosTarjetas());
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTickets(@RequestParam LocalDate inicio, @RequestParam LocalDate fin) {
        return ResponseEntity.ok(reporteService.obtenerTicketsDeVenta(inicio, fin));
    }

    @GetMapping("/usuarios/cantidad")
    public ResponseEntity<Long> obtenerCantidadUsuarios() {
        return ResponseEntity.ok(reporteService.obtenerCantidadUsuarios());
    }
}
