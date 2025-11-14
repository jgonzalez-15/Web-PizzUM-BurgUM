package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tarjetas")
    public ResponseEntity<List<MedioDePagoDTO>> obtenerTarjetas() {
        return ResponseEntity.ok(reporteService.obtenerDatosTarjetas());
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTickets(@RequestParam LocalDate fecha) {
        return ResponseEntity.ok(reporteService.obtenerTicketsDeVenta(fecha));
    }

    @GetMapping("/usuarios/cantidad")
    public ResponseEntity<List<Object>> obtenerCantidadUsuarios() {
        return ResponseEntity.ok(reporteService.obtenerCantidadUsuarios());
    }
}
