package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.AdministradorUpdateDTO;
import uy.um.edu.pizzumandburgum.dto.update.ClienteUpdateDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.AdministradorService;

import java.util.List;

@RestController
@RequestMapping("/api/administrador")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class AdministradorController {
    @Autowired
    private AdministradorService administradorService;


    @PostMapping("/agregarAdmin")
    public ResponseEntity<AdministradorResponseDTO> agregarAdmin(@Validated @RequestBody AdministradorResponseDTO dto, HttpSession sesion) {
        AdministradorResponseDTO admin = administradorService.agregarAdmin(dto);

        sesion.setAttribute("email", admin.getEmail());
        sesion.setAttribute("rol", "ADMIN");



        return ResponseEntity.ok(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<AdministradorResponseDTO> login(@RequestBody AdministradorRequestDTO dto,
                                                          HttpSession session) {

        AdministradorResponseDTO adminDTO = administradorService.login(dto);

        // Guardar sesión (opcional)
        session.setAttribute("email", adminDTO.getEmail());
        session.setAttribute("rol", "ADMIN");

        return ResponseEntity.ok(adminDTO);
    }

    @PostMapping("/cerrarSesion")
    public ResponseEntity<String>cerrarSesion(HttpSession sesion){
        sesion.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @PutMapping("/{email}/perfil")
    public ResponseEntity<AdministradorResponseDTO> editarPerfil(
            @PathVariable String email,
            @RequestBody AdministradorUpdateDTO dto) {
        AdministradorResponseDTO response = administradorService.editarPerfil(email, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AdministradorResponseDTO>> mostrarAdministradores() {
        List<AdministradorResponseDTO> administradores = administradorService.listarAdministradores();
        return ResponseEntity.ok(administradores);
    }
}
