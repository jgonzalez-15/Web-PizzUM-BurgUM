package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.mapper.HamburguesaMapper;
import uy.um.edu.pizzumandburgum.service.Interfaces.HamburguesaService;

import java.util.List;

@RestController
@RequestMapping("/api/hamburguesa")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class HamburguesaController {
    @Autowired
    private HamburguesaService hamburguesaService;

    @Autowired
    private HamburguesaMapper hamburguesaMapper;

    @PostMapping("/crearHamburguesa")
    public ResponseEntity<HamburguesaResponseDTO> crearHamburguesa(@RequestBody HamburguesaRequestDTO dto) {
        HamburguesaResponseDTO nueva = hamburguesaService.crearHamburguesa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<HamburguesaResponseDTO>> mostrarHamburguesas() {
        List<HamburguesaResponseDTO> hamburguesas = hamburguesaService.listarHamburguesas();
        return ResponseEntity.ok(hamburguesas);
    }
}
