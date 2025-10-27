package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteDomicilioService;

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
}
