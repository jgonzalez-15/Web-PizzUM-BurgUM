package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.CreacionResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.ClienteUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Creacion;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteService;
import uy.um.edu.pizzumandburgum.service.Interfaces.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class ClienteController {
    @Autowired
    private ClienteService clienteService;



    @PostMapping("/registrar")
    public ResponseEntity<ClienteResponseDTO> registrar(@Validated @RequestBody ClienteRequestDTO dto,HttpSession session) {
        ClienteResponseDTO cliente = clienteService.registrarCliente(dto);

        session.setAttribute("email", cliente.getEmail());
        session.setAttribute("rol", "CLIENTE");
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/login")
    public ResponseEntity<ClienteResponseDTO>login(@Validated @RequestBody ClienteRequestDTO dto, HttpSession sesion){
        ClienteResponseDTO cliente = clienteService.login(dto.getEmail(), dto.getContrasenia());
        // Guardar datos en la sesión
        sesion.setAttribute("email", cliente.getEmail());
        sesion.setAttribute("rol", "CLIENTE");
        sesion.setAttribute("nombre", cliente.getNombre());
        return ResponseEntity.ok(cliente);
    }
    @PostMapping("/cerrarSesion")
    public ResponseEntity<String>cerrarSesion(HttpSession sesion){
        sesion.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
    @GetMapping("/{idCliente}/historial-pedidos")
    public ResponseEntity<List<Pedido>> listarHistorialPedidos(@PathVariable("idCliente") String idCliente) {
        List<Pedido> historial = clienteService.historialPedido(idCliente);
        return ResponseEntity.ok(historial);
    }
    @PutMapping("/{email}/perfil")
    public ResponseEntity<ClienteResponseDTO> editarPerfil(
            @PathVariable String email,
            @RequestBody ClienteUpdateDTO dto) {
        ClienteResponseDTO response = clienteService.editarPerfil(email, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteResponseDTO>> mostrarCliente() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }


    @GetMapping("/{idCliente}/favoritas")
    public ResponseEntity<List<CreacionResponseDTO>> mostrarCreacionesFavoritas(@PathVariable("idCliente")String idCliente,HttpSession sesion){
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        List<CreacionResponseDTO> creaciones = clienteService.mostrarCreacionesFavoritas(idCliente);
        return ResponseEntity.ok(creaciones);
    }

    @GetMapping("/{idCliente}/creaciones")
    public ResponseEntity<List<CreacionResponseDTO>> mostrarCreaciones(@PathVariable("idCliente")String idCliente){
        List<CreacionResponseDTO> creaciones = clienteService.mostrarCreaciones(idCliente);
        return ResponseEntity.ok(creaciones);
    }

    @PostMapping("/asociarHamburguesa")
    public ResponseEntity<HamburguesaResponseDTO> asociarHamburguesa(
            @RequestBody String email,
            @RequestBody Long idHamburguesa) {

        HamburguesaResponseDTO response = clienteService.asociarHamburguesa(email, idHamburguesa);
        return ResponseEntity.ok(response);
    }

    // Obtener todos los pedidos de un cliente
    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerPedidosPorCliente(@PathVariable String clienteId) {
        List<PedidoResponseDTO> pedidos = clienteService.obtenerPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    


}
