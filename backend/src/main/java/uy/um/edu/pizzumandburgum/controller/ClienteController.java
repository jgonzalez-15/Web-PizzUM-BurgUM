package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteService;

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
    public ResponseEntity<ClienteResponseDTO>login(@Validated @RequestBody ClienteRequestDTO dto, HttpSession session){
        ClienteResponseDTO cliente = clienteService.login(dto.getEmail(), dto.getContrasenia());
        // Guardar datos en la sesión
        session.setAttribute("email", cliente.getEmail());
        session.setAttribute("rol", "CLIENTE"); // 🔹 asignamos el rol
        session.setAttribute("nombre", cliente.getNombre());
        return ResponseEntity.ok(cliente);
    }
    @PostMapping("/cerrarSesion")
    public ResponseEntity<String>cerrarSesion(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
    @GetMapping("/{idCliente}/historial-pedidos")
    public ResponseEntity<List<Pedido>> listarHistorialPedidos(@PathVariable String idCliente) {
        List<Pedido> historial = clienteService.historialPedido(idCliente);
        return ResponseEntity.ok(historial);
    }

}
