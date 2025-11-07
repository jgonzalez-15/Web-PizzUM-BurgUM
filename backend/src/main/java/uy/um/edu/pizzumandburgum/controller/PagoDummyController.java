package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.PagoDummyRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PagoDummyResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.PagoDummyService;

@RestController
@RequestMapping("/api/dummy")
@PreAuthorize("hasAuthority('CLIENTE')")
@CrossOrigin(origins = "http://localhost:5173")
public class PagoDummyController {

    @Autowired
    private PagoDummyService pagoDummyService;

    @PostMapping("/procesarPago")
    public ResponseEntity<PagoDummyResponseDTO> procesarPago(@RequestBody PagoDummyRequestDTO dto) {
        PagoDummyResponseDTO retornar = pagoDummyService.procesarPago(dto);
        return ResponseEntity.ok(retornar);
    }
}
