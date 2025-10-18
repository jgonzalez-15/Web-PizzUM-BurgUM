package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.service.MedioDePagoService;
import uy.um.edu.pizzumandburgum.service.PedidoService;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class PedidoController {
    @Autowired
    private MedioDePagoService medioDePagoService;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/realizar")
    public ResponseEntity<PedidoResponseDTO> realizarPedido(@RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO pedido = pedidoService.realizarPedido(dto.getClienteAsignado().getEmail(),dto.getDomicilio().getDireccion(),dto,dto.getMedioDePago().getNumero());
        return ResponseEntity.ok(pedido);
    }
}
