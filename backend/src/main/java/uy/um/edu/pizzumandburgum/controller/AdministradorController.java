package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.AdministradorUpdateDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.AdministradorService;

import java.util.List;

@RestController
@RequestMapping("/api/administrador")
@CrossOrigin(origins = "http://localhost:5173")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;


    @PostMapping("/agregarAdmin")
    public ResponseEntity<AdministradorResponseDTO> agregarAdmin(@Validated @RequestBody AdministradorResponseDTO dto, HttpSession sesion) {
        String rol = (String) sesion.getAttribute("rol");
        if (rol == null || !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        AdministradorResponseDTO admin = administradorService.agregarAdmin(dto);

        return ResponseEntity.ok(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<AdministradorResponseDTO> login(@RequestBody AdministradorRequestDTO dto, HttpSession sesion) {
        AdministradorResponseDTO adminDTO = administradorService.login(dto);

        sesion.setAttribute("email", adminDTO.getEmail());
        sesion.setAttribute("rol", "ADMIN");

        return ResponseEntity.ok(adminDTO);
    }

    @PostMapping("/cerrarSesion")
    public ResponseEntity<String> cerrarSesion(HttpSession sesion){
        String email = (String) sesion.getAttribute("email");

        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay sesión activa");
        }

        sesion.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @PutMapping("/{email}/perfil")
    public ResponseEntity<AdministradorResponseDTO> editarPerfil(@PathVariable String email, @RequestBody AdministradorUpdateDTO dto, HttpSession sesion) {
        String emailSesion = (String) sesion.getAttribute("email");
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        if (emailSesion == null || !email.equals(emailSesion)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        AdministradorResponseDTO response = administradorService.editarPerfil(email, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AdministradorResponseDTO>> mostrarAdministradores(HttpSession sesion) {
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<AdministradorResponseDTO> administradores = administradorService.listarAdministradores();
        return ResponseEntity.ok(administradores);
    }
}
