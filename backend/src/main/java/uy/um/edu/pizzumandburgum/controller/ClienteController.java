package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRegistrarRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.*;
import uy.um.edu.pizzumandburgum.dto.update.ClienteUpdateDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/registrar")
    public ResponseEntity<ClienteResponseDTO> registrar(@Validated @RequestBody ClienteRegistrarRequestDTO dto) {
        ClienteResponseDTO cliente = clienteService.registrarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PostMapping("/login")
    public ResponseEntity<ClienteResponseDTO> login(@Validated @RequestBody ClienteRequestDTO dto, HttpSession sesion) {
        ClienteResponseDTO cliente = clienteService.login(dto.getEmail(), dto.getContrasenia());

        sesion.setAttribute("email", cliente.getEmail());
        sesion.setAttribute("rol", "CLIENTE");
        sesion.setAttribute("nombre", cliente.getNombre());

        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/cerrarSesion")
    public ResponseEntity<String> cerrarSesion(HttpSession sesion) {
        sesion.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @GetMapping("/{idCliente}/historial-pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> listarHistorialPedidos(@PathVariable("idCliente") String idCliente) {
        List<PedidoResponseDTO> historial = clienteService.historialPedido(idCliente);
        return ResponseEntity.ok(historial);
    }

    @PutMapping("/{email}/perfil")
    public ResponseEntity<ClienteResponseDTO> editarPerfil(@PathVariable String email, @RequestBody ClienteUpdateDTO dto) {
        ClienteResponseDTO response = clienteService.editarPerfil(email, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteResponseDTO>> mostrarCliente(HttpSession sesion) {
        List<ClienteResponseDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{idCliente}/creaciones")
    public ResponseEntity<List<CreacionResponseDTO>> mostrarCreaciones(@PathVariable("idCliente")String idCliente){
        List<CreacionResponseDTO> creaciones = clienteService.mostrarCreaciones(idCliente);
        return ResponseEntity.ok(creaciones);
    }

    @PostMapping("/asociarHamburguesa")
    public ResponseEntity<HamburguesaResponseDTO> asociarHamburguesa(@RequestBody String email, @RequestBody Long idHamburguesa) {
        HamburguesaResponseDTO response = clienteService.asociarHamburguesa(email, idHamburguesa);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/asociarPizza")
    public ResponseEntity<PizzaResponseDTO> asociarPizza(@RequestBody String email, @RequestBody Long idPizza) {
        PizzaResponseDTO response = clienteService.asociarPizza(email, idPizza);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerPedidosPorCliente(@PathVariable String clienteId) {
        List<PedidoResponseDTO> pedidos = clienteService.obtenerPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{clienteId}/obtenerPerfil")
    public ResponseEntity<ClienteResponseDTO> obtenerPerfil(@PathVariable String clienteId) {
        ClienteResponseDTO cliente = clienteService.obtenerCliente(clienteId);
        return ResponseEntity.ok(cliente);
    }


    


}
