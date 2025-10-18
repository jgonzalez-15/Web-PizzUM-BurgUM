package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorDTO;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.ProductoDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.mapper.ProductoMapper;
import uy.um.edu.pizzumandburgum.service.AdministradorService;
import uy.um.edu.pizzumandburgum.service.ProductoService;

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

    /*@PostMapping("/login")
    public ResponseEntity<AdministradorDTO>login(@Validated @RequestBody AdministradorRequestDTO dto, HttpSession session){
        AdministradorDTO admin = administradorService.login(dto.getEmail(), dto.getContrasenia());
        // Guardar datos en la sesión
        session.setAttribute("email", admin.getEmail());
        session.setAttribute("rol", "Administrador"); // 🔹 asignamos el rol
        session.setAttribute("nombre", admin.getNombre());
        return ResponseEntity.ok(admin);
    }*/

    /*@PostMapping("/cerrarSesion")
    public ResponseEntity<String>cerrarSesion(HttpSession session){
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }*/
}
