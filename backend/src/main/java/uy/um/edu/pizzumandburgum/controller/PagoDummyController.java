package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uy.um.edu.pizzumandburgum.dto.request.PagoDummyRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PagoDummyResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.PagoDummyService;

public class PagoDummyController {

    @Autowired
    private PagoDummyService pagoDummyService;

    @PostMapping
    public ResponseEntity<PagoDummyResponseDTO> procesarPago(@RequestBody PagoDummyRequestDTO dto) {
        PagoDummyResponseDTO retornar = pagoDummyService.procesarPago(dto);
        return ResponseEntity.ok(retornar);
    }
}
