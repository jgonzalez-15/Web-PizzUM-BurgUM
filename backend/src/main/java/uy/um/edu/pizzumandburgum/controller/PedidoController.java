package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.mapper.PedidoMapper;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.MedioDePagoService;
import uy.um.edu.pizzumandburgum.service.Interfaces.PedidoService;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class PedidoController {
    @Autowired
    private MedioDePagoService medioDePagoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping("/realizar")
    public ResponseEntity<PedidoResponseDTO> realizarPedido(@RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO pedido = pedidoService.realizarPedido(dto);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}/cancelarPedido")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long idPedido){
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new PedidoNoEncontradoException());
        if (pedido.getEstado().equals("En Cola")){
        }
        pedidoService.eliminarPedido(idPedido);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/estado")
    public ResponseEntity<String> consultarEstado(@PathVariable Long id) {
        String estado = pedidoService.consultarEstado(id);
        return ResponseEntity.ok(estado);
    }

    @PutMapping("/{id}/cambiarEstado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        pedidoService.cambiarEstado(id, estado);
        return ResponseEntity.ok().build();
    }
}
