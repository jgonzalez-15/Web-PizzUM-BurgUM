package uy.um.edu.pizzumandburgum.Controladores.Historicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.DTOs.Response.Historicos.HistoricoClienteResponseDTO;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos.HistoricoClienteModificacionesService;

import java.util.List;


@RestController
@RequestMapping("/api/hCliente")
@CrossOrigin(origins = "http://localhost:5173")
public class HistoricoClienteModificacionesController {

    @Autowired
    private HistoricoClienteModificacionesService historicoClienteService;

    @GetMapping("/cliente/{emailCliente}")
    public ResponseEntity<List<HistoricoClienteResponseDTO>> listarHistoricosPorCliente(@PathVariable String emailCliente) {
        List<HistoricoClienteResponseDTO> historicos = historicoClienteService.listarHistoricosPorCliente(emailCliente);
        return ResponseEntity.ok(historicos);
    }


    @GetMapping("/listar")
    public ResponseEntity<List<HistoricoClienteResponseDTO>> listarTodosLosHistoricos() {
        List<HistoricoClienteResponseDTO> historicos = historicoClienteService.listarTodosLosHistoricos();
        return ResponseEntity.ok(historicos);
    }


    @GetMapping("/tipo")
    public ResponseEntity<List<HistoricoClienteResponseDTO>> listarHistoricosPorTipo(@RequestBody String tipo) {
        List<HistoricoClienteResponseDTO> historicos = historicoClienteService.listarHistoricosPorTipo(tipo);
        return ResponseEntity.ok(historicos);
    }
}
