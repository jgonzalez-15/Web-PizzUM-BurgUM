package uy.um.edu.pizzumandburgum.controller.Historicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.response.Historicos.HistoricoProductoResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.Historicos.HistoricoProductoModificacionService;

import java.util.List;


@RestController
@RequestMapping("/api/hProducto")
@CrossOrigin(origins = "http://localhost:5173")
public class HistoricoProductoModificacionesController {
    @Autowired
    private HistoricoProductoModificacionService historicoProductoService;

    @GetMapping ("listar")
    public ResponseEntity<List<HistoricoProductoResponseDTO>> listarTodosLosHistoricos() {
        List<HistoricoProductoResponseDTO> historicos = historicoProductoService.listarTodosLosHistoricos();
        return ResponseEntity.ok(historicos);
    }


    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<HistoricoProductoResponseDTO>> listarHistoricosPorProducto(@PathVariable Long productoId) {
        List<HistoricoProductoResponseDTO> historicos = historicoProductoService.listarHistoricosPorProducto(productoId);
        return ResponseEntity.ok(historicos);
    }


    @GetMapping("/tipo")
    public ResponseEntity<List<HistoricoProductoResponseDTO>> listarHistoricosPorTipo(@RequestBody String tipo) {
        List<HistoricoProductoResponseDTO> historicos = historicoProductoService.listarHistoricosPorTipo(tipo);
        return ResponseEntity.ok(historicos);
    }
}
