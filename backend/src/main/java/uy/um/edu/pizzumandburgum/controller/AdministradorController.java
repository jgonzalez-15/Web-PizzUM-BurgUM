package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.AdministradorService;

@RestController
@RequestMapping("/api/administrador")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class AdministradorController {
    @Autowired
    private AdministradorService administradorService;




    @PostMapping("/agregarAdmin")
    public ResponseEntity<AdministradorDTO> agregarAdmin(@Validated @RequestBody AdministradorDTO dto, HttpSession session) {
        AdministradorDTO admin = administradorService.agregarAdmin(dto);

        session.setAttribute("email", admin.getEmail());
        session.setAttribute("rol", "ADMIN");



        return ResponseEntity.ok(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<AdministradorDTO> login( @RequestParam String email,
                                                  @RequestParam String contrasenia,
                                                  HttpSession session) {

        AdministradorDTO adminDTO = administradorService.login(email, contrasenia);

        // Guardar sesión (opcional)
        session.setAttribute("email", adminDTO.getEmail());
        session.setAttribute("rol", "ADMIN");

        return ResponseEntity.ok(adminDTO);
    }

    @PostMapping("/cerrarSesion")
    public ResponseEntity<String>cerrarSesion(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
}
