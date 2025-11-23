package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.DomicilioUpdateDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.DomicilioService;

@RestController
@RequestMapping("/api/domicilio")
@PreAuthorize("hasAuthority('CLIENTE')")
@CrossOrigin(origins = "http://localhost:5173")
public class DomicilioController {

    @Autowired
    private DomicilioService domicilioService;

    @PostMapping("/crearDomicilio")
    public ResponseEntity<DomicilioResponseDTO> crearDomicilio(@RequestBody DomicilioRequestDTO request) {
        DomicilioResponseDTO response = domicilioService.crearDomicilio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/editar/{idDomicilio}")
    public ResponseEntity<DomicilioResponseDTO> editarDomicilio(@PathVariable Long idDomicilio, @RequestBody DomicilioUpdateDTO dto) {
        DomicilioResponseDTO response = domicilioService.editarPerfil(idDomicilio, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{domicilioId}/cliente")
    public ResponseEntity<Void> eliminarDomicilio(@PathVariable Long domicilioId, @PathVariable String clienteId) {
        domicilioService.eliminarDomicilio(domicilioId, clienteId);
        return ResponseEntity.noContent().build();
    }
}
