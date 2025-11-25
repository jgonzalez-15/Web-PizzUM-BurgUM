package uy.um.edu.pizzumandburgum.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.DTOs.Request.FavoritoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.FavoritoResponseDTO;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.FavoritoService;

import java.util.List;

@RestController
@RequestMapping("/api/favorito")
@PreAuthorize("hasAuthority('CLIENTE')")
@CrossOrigin(origins = "http://localhost:5173")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/listar")
    public ResponseEntity<List<FavoritoResponseDTO>> mostrarCreacionesFavoritas(Authentication authentication){
        String idCliente = authentication.getName();
        List<FavoritoResponseDTO> favoritos = favoritoService.mostrarCreacionesFavoritas(idCliente);
        return ResponseEntity.ok(favoritos);
    }

    @PostMapping("/agregar")
    public ResponseEntity<FavoritoResponseDTO> agregarFavorito(@RequestBody FavoritoRequestDTO dto) {
        FavoritoResponseDTO nuevoFavorito = favoritoService.agregarFavorito(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoFavorito);
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<Void> eliminarFavorito(@PathVariable Long id) {
        favoritoService.eliminarFavorito(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritoResponseDTO> obtenerFavoritoPorId(@PathVariable Long id) {
        FavoritoResponseDTO favorito = favoritoService.obtenerFavoritoPorId(id);
        return ResponseEntity.ok(favorito);
    }


}
