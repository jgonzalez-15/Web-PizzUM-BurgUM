package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteDomicilioService;

import java.util.List;

@RestController
@RequestMapping("/api/clienteDomicilio")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteDomicilioController {

    @Autowired
    private ClienteDomicilioService clienteDomicilioService;

    @PostMapping("/asociarDomicilio")
    public ResponseEntity<ClienteDomicilioResponseDTO> agregarDomicilioACliente(@RequestBody ClienteDomicilioRequestDTO dto) {

        ClienteDomicilioResponseDTO response = clienteDomicilioService.agregarDomicilio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{emailCliente}/listar")
    public ResponseEntity<List<DomicilioResponseDTO>> listarDomiciliosDeCliente(@PathVariable String emailCliente) {
        List<DomicilioResponseDTO> domicilios = clienteDomicilioService.listarDomiciliosDeCliente(emailCliente);
        return ResponseEntity.ok(domicilios);
    }

    @DeleteMapping("/eliminar/{emailCliente}/{idDomicilio}")
    public ResponseEntity<Void> eliminarDomicilioDeCliente(@PathVariable String emailCliente, @PathVariable Long idDomicilio) {

        clienteDomicilioService.eliminarDomicilioDeCliente(emailCliente, idDomicilio);
        return ResponseEntity.noContent().build();
    }
}
