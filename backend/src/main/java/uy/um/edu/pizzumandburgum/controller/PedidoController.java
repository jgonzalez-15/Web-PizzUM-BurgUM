package uy.um.edu.pizzumandburgum.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.CalificacionRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.PedidoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PreAuthorize("hasAuthority('CLIENTE')")
    @PostMapping("/realizarPedido")
    public ResponseEntity<PedidoResponseDTO> realizarPedido(@RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO pedido = pedidoService.realizarPedido(dto);
        return ResponseEntity.ok(pedido);
    }

    @PreAuthorize("hasAuthority('CLIENTE') or hasAuthority('ADMIN')")
    @DeleteMapping("/{id}/cancelarPedido")
    public ResponseEntity<Void> cancelarPedido(@PathVariable ("id") Long id){
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(PedidoNoEncontradoException::new);

        if (pedido.getEstado().equals("En Cola")) {
            pedidoService.eliminarPedido(id);
        }
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasAuthority('CLIENTE') or hasAuthority('ADMIN')")
    @GetMapping("/{id}/estado")
    public ResponseEntity<String> consultarEstado(@PathVariable ("id") Long id) {
        String estado = pedidoService.consultarEstado(id);
        return ResponseEntity.ok(estado);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/cambiarEstado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable ("id") Long id) {
        pedidoService.cambiarEstado(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/enCurso")
    public ResponseEntity<List<PedidoResponseDTO>> pedidosEnCurso(){
        List<PedidoResponseDTO> pedidoResponseDTOS = pedidoService.pedidosEnCurso();
        return ResponseEntity.ok(pedidoResponseDTOS);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/rango/{fechaInicio}/{fechaFin}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosPorRango(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPedidosPorRangoFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(pedidos);
    }

    @PreAuthorize("hasAuthority('CLIENTE') or hasAuthority('ADMIN')")
    @GetMapping("/ver/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PreAuthorize("hasAuthority('CLIENTE')")
    @PostMapping("/calificar")
    public ResponseEntity<Void> calificarPedido(@RequestBody CalificacionRequestDTO dto) {
        pedidoService.calificar(dto.getIdPedido(), dto.getCalificacion());
        return ResponseEntity.noContent().build();
    }
}
