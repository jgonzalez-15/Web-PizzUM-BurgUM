package uy.um.edu.pizzumandburgum.Controladores;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.DTOs.Request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Update.AdministradorUpdateDTO;
import uy.um.edu.pizzumandburgum.Seguridad.JwtUtil;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.AdministradorService;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/administrador")
@CrossOrigin(origins = "http://localhost:5173")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/agregarAdmin")
    public ResponseEntity<AdministradorResponseDTO> agregarAdmin(@Validated @RequestBody AdministradorRequestDTO dto) {
        AdministradorResponseDTO admin = administradorService.agregarAdmin(dto);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/cerrarSesion")
    public ResponseEntity<String> cerrarSesion(HttpServletRequest request, HttpServletResponse response){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            jwtUtil.invalidateToken(token);
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie refreshCookie = new Cookie("refreshToken", null);
        refreshCookie.setPath("/");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(0);
        refreshCookie.setSecure(false);
        response.addCookie(refreshCookie);

        return ResponseEntity.ok("Sesión cerrada correctamente");
    }

    @PutMapping("/editar/{email}")
    public ResponseEntity<AdministradorResponseDTO> editarPerfil(@PathVariable String email, @RequestBody AdministradorUpdateDTO dto) {
        AdministradorResponseDTO response = administradorService.editarPerfil(email, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AdministradorResponseDTO>> mostrarAdministradores() {
        List<AdministradorResponseDTO> administradores = administradorService.listarAdministradores();
        return ResponseEntity.ok(administradores);
    }

    @DeleteMapping("/eliminar/{email}")
    public ResponseEntity<String> eliminarAdmin(@PathVariable String email) {
        administradorService.eliminarAdministrador(email);
        return ResponseEntity.ok("Administrador eliminado correctamente");
    }

    @PutMapping("/{email}/domicilio/{idDomicilio}")
    public ResponseEntity<Void> asociarDomicilio(@PathVariable String email, @PathVariable Long idDomicilio) {
        administradorService.asociarDomicilio(idDomicilio, email);
        return ResponseEntity.noContent().build();
    }

}
