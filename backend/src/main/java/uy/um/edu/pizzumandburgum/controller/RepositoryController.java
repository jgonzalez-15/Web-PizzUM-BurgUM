package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.TicketResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ReporteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:5173")

public class RepositoryController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/tarjeta/{numero}")
    public ResponseEntity<ClienteResponseDTO> obtenerTarjetas(@PathVariable Long numero) {
        return ResponseEntity.ok(reporteService.obtenerClientePorTarjeta(numero));
    }

    @GetMapping("/tickets/{fecha}")
    public ResponseEntity<List<TicketResponseDTO>> obtenerTickets(@PathVariable LocalDate fecha) {
        return ResponseEntity.ok(reporteService.obtenerTicketsDeVenta(fecha));
    }

    @GetMapping("/administradores")
    public ResponseEntity<List<AdministradorResponseDTO>> obtenerCantidadUsuarios() {
        return ResponseEntity.ok(reporteService.obtenerCantidadUsuarios());
    }
}
