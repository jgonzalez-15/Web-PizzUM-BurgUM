package uy.um.edu.pizzumandburgum.Controladores;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.DTOs.Request.AdministradorLoginRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Request.AuthRequest;
import uy.um.edu.pizzumandburgum.DTOs.Response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.AuthResponse;
import uy.um.edu.pizzumandburgum.DTOs.Response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.Seguridad.JwtUtil;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.AdministradorService;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.ClienteService;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AdministradorService administradorService;


    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest dto) {
        String email = dto.getEmail();
        String contrasenia = dto.getContrasenia();

        try {
            ClienteResponseDTO cliente = clienteService.login(email, contrasenia);
            String token = jwtUtil.generateToken(email, "CLIENTE");
            String refreshToken = jwtUtil.generateRefreshToken(email, "CLIENTE");

            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("Strict")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();

            return ResponseEntity.ok()
                    .header("Set-Cookie", cookie.toString())
                    .body(new AuthResponse(token, "CLIENTE", cliente));
        } catch (Exception e) {}

        try {
            AdministradorResponseDTO admin = administradorService.login(new AdministradorLoginRequestDTO(email, contrasenia));
            String token = jwtUtil.generateToken(email, "ADMIN");
            String refreshToken = jwtUtil.generateRefreshToken(email, "ADMIN");

            ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("Strict")
                    .maxAge(60 * 60)
                    .build();

            return ResponseEntity.ok()
                    .header("Set-Cookie", cookie.toString())
                    .body(new AuthResponse(token, "ADMIN", admin));
        } catch (Exception e) {}

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    @GetMapping("/resfrescar")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(c -> "refreshToken".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (refreshToken == null || !jwtUtil.isTokenValid(refreshToken))
            return ResponseEntity.status(401).build();

        String email = jwtUtil.extractUsername(refreshToken);
        String role = jwtUtil.extractRole(refreshToken);
        Object usuario = getUserData(email, role);

        if (usuario == null) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtUtil.generateToken(email, role);
        return ResponseEntity.ok(new AuthResponse(token, role, usuario));
    }

    private Object getUserData(String email, String role) {
        try {
            if ("CLIENTE".equals(role)) {
                return clienteService.obtenerCliente(email);
            } else if ("ADMIN".equals(role)) {
                return administradorService.obtenerAdministrador(email);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
