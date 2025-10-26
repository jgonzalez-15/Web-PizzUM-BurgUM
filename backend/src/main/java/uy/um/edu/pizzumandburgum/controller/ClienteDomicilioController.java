package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteDomicilioService;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteDomicilioController {
    @Autowired
    private ClienteDomicilioService clienteDomicilioService;

    @PostMapping
    public ResponseEntity<String> agregarDomicilioACliente(
            @RequestBody ClienteDomicilioRequestDTO request) {

        clienteDomicilioService.agregarDomicilio(
                request.getEmail(),
                request.getIdDomicilio()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("Domicilio agregado exitosamente al cliente");
    }
}
