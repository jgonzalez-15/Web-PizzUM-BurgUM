package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteDomicilioService;

import java.util.List;

@RestController
@RequestMapping("/api/clienteDomicilio")
@PreAuthorize("hasAuthority('CLIENTE')")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteDomicilioController {

    @Autowired
    private ClienteDomicilioService clienteDomicilioService;

    @PostMapping("/asociarDomicilio")
    public ResponseEntity<Void> agregarDomicilioACliente(@RequestBody ClienteDomicilioRequestDTO dto) {
        clienteDomicilioService.agregarDomicilio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<DomicilioResponseDTO>> listarDomiciliosDeCliente(Authentication authentication) {
        String emailCliente = authentication.getName();
        List<DomicilioResponseDTO> domicilios = clienteDomicilioService.listarDomiciliosDeCliente(emailCliente);
        return ResponseEntity.ok(domicilios);
    }

    @DeleteMapping("/eliminar/{idDomicilio}")
    public ResponseEntity<Void> eliminarDomicilioDeCliente(Authentication authentication, @PathVariable Long idDomicilio) {
        String emailCliente = authentication.getName();
        clienteDomicilioService.eliminarDomicilioDeCliente(emailCliente, idDomicilio);
        return ResponseEntity.noContent().build();
    }
}
