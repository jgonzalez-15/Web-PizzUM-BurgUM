package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.MedioDePagoService;

@RestController
@RequestMapping("/api/mediodepago")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class MedidoDePagoController {
    @Autowired
    private MedioDePagoService medioDePagoService;

    @PostMapping("añadir")
    public ResponseEntity<MedioDePagoDTO> aniadirMedioDePago(@RequestBody MedioDePagoRequestDTO request) {
        MedioDePagoDTO response = medioDePagoService.aniadirMedioDePago(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
