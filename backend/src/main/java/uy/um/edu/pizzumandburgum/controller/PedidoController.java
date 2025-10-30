package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<PedidoResponseDTO> realizarPedido(@RequestBody PedidoRequestDTO dto,HttpSession sesion) {
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        PedidoResponseDTO pedido = pedidoService.realizarPedido(dto);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}/cancelarPedido")
    public ResponseEntity<Void> cancelarPedido(@PathVariable ("id") Long id,HttpSession sesion){
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNoEncontradoException());
        if (pedido.getEstado().equals("En Cola")){
        }
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/estado")
    public ResponseEntity<String> consultarEstado(@PathVariable ("id") Long id) {
        String estado = pedidoService.consultarEstado(id);
        return ResponseEntity.ok(estado);
    }

    @PutMapping("/{id}/cambiarEstado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable ("id") Long id, HttpSession sesion) {
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        pedidoService.cambiarEstado(id);
        return ResponseEntity.ok().build();
    }
}
