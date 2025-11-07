package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.AdministradorUpdateDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.AdministradorService;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/administrador")
@CrossOrigin(origins = "http://localhost:5173")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;


    @PostMapping("/agregarAdmin")
    public ResponseEntity<AdministradorResponseDTO> agregarAdmin(@Validated @RequestBody AdministradorResponseDTO dto) {
        AdministradorResponseDTO admin = administradorService.agregarAdmin(dto);

        return ResponseEntity.ok(admin);
    }

    @PostMapping("/cerrarSesion")
    public ResponseEntity<String> cerrarSesion(HttpSession sesion){
        sesion.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @PutMapping("/perfil")
    public ResponseEntity<AdministradorResponseDTO> editarPerfil(Authentication authentication, @RequestBody AdministradorUpdateDTO dto) {
        String email = authentication.getName();
        AdministradorResponseDTO response = administradorService.editarPerfil(email, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AdministradorResponseDTO>> mostrarAdministradores() {
        List<AdministradorResponseDTO> administradores = administradorService.listarAdministradores();
        return ResponseEntity.ok(administradores);
    }
}
