package uy.um.edu.pizzumandburgum.controller.Historicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoClienteResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoClienteModificacionesService;

import java.util.List;

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
