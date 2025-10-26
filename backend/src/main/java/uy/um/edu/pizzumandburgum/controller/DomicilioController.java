package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.DomicilioService;
import java.util.List;


@RestController
@RequestMapping("/api/domicilio")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class DomicilioController {
    @Autowired
    private DomicilioService domicilioService;

    @PostMapping("/crearDomicilio")
    public ResponseEntity<DomicilioResponseDTO> crearDomicilio(
            @RequestBody DomicilioRequestDTO request) {

        DomicilioResponseDTO response = domicilioService.crearDomicilio(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/listarDomicilio")
    public ResponseEntity<List<DomicilioResponseDTO>> listarDomicilio() {
        List<DomicilioResponseDTO> domicilios = domicilioService.listarDomicilios();
        return ResponseEntity.ok(domicilios);
    }

}
