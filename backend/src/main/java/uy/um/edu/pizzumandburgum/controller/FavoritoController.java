package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.FavoritoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.FavoritoResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.FavoritoService;

import java.util.List;

@RestController
@RequestMapping("/api/favorito")
@CrossOrigin(origins = "http://localhost:5173")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/{idCliente}/listar")
    public ResponseEntity<List<FavoritoResponseDTO>> mostrarCreacionesFavoritas(@PathVariable("idCliente")String idCliente, HttpSession sesion){
//        String rol = (String) sesion.getAttribute("rol");
//
//        if (rol == null || !rol.equals("CLIENTE")) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
//        }

        List<FavoritoResponseDTO> favoritos = favoritoService.mostrarCreacionesFavoritas(idCliente);
        return ResponseEntity.ok(favoritos);
    }

    @PostMapping("/agregar")
    public ResponseEntity<FavoritoResponseDTO> agregarFavorito(@RequestBody FavoritoRequestDTO dto, HttpSession sesion) {

        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        FavoritoResponseDTO nuevoFavorito = favoritoService.agregarFavorito(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoFavorito);
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<Void> eliminarFavorito(@PathVariable Long id, HttpSession sesion) {

        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        favoritoService.eliminarFavorito(id);
        return ResponseEntity.noContent().build();
    }


}
