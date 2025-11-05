package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.HamburguesaService;

import java.util.List;

@RestController
@RequestMapping("/api/hamburguesa")
@CrossOrigin(origins = "http://localhost:5173")
public class HamburguesaController {

    @Autowired
    private HamburguesaService hamburguesaService;

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

    @GetMapping("/{idCreacion}/ingredientes")
    public ResponseEntity<List<ProductoResponseDTO>> mostrarIngredientesHamburguesa(@PathVariable Long idCreacion){
        List<ProductoResponseDTO> ingredientes = hamburguesaService.obtenerIngredientesHamburguesa(idCreacion);
        return ResponseEntity.ok(ingredientes);
    }
}
