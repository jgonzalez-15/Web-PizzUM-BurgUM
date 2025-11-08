package uy.um.edu.pizzumandburgum.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRegistrarRequestDTO;
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

    @PostMapping("/cerrarSesion")
    public ResponseEntity<String> cerrarSesion(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setSecure(false);
        response.addCookie(cookie);
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @GetMapping("{email}/historial-pedidos")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<List<PedidoResponseDTO>> listarHistorialPedidos(Authentication authentication) {
        String email = authentication.getName(); // Extracted from JWT subject
        List<PedidoResponseDTO> historial = clienteService.historialPedido(email);
        return ResponseEntity.ok(historial);
    }


    @PutMapping("/perfil")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<ClienteResponseDTO> editarPerfil(Authentication authentication, @RequestBody ClienteUpdateDTO dto) {
        String email = authentication.getName();
        ClienteResponseDTO response = clienteService.editarPerfil(email, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteResponseDTO>> mostrarCliente(HttpSession sesion) {
        List<ClienteResponseDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/creaciones")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<List<CreacionResponseDTO>> mostrarCreaciones(Authentication authentication) {
        String idCliente = authentication.getName();
        List<CreacionResponseDTO> creaciones = clienteService.mostrarCreaciones(idCliente);
        return ResponseEntity.ok(creaciones);
    }

    @PostMapping("/asociarHamburguesa")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<HamburguesaResponseDTO> asociarHamburguesa(Authentication authentication, @RequestBody Long idHamburguesa) {
        String email = authentication.getName();
        HamburguesaResponseDTO response = clienteService.asociarHamburguesa(email, idHamburguesa);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/asociarPizza")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<PizzaResponseDTO> asociarPizza(Authentication authentication, @RequestBody Long idPizza) {
        String email = authentication.getName();
        PizzaResponseDTO response = clienteService.asociarPizza(email, idPizza);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pedidos")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<List<PedidoResponseDTO>> obtenerPedidosPorCliente(Authentication authentication) {
        String clienteId = authentication.getName();
        List<PedidoResponseDTO> pedidos = clienteService.obtenerPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/obtenerPerfil")
    @PreAuthorize("hasAuthority('CLIENTE')")
    public ResponseEntity<ClienteResponseDTO> obtenerPerfil(Authentication authentication) {
        String clienteId = authentication.getName();
        ClienteResponseDTO cliente = clienteService.obtenerCliente(clienteId);
        return ResponseEntity.ok(cliente);
    }
}
