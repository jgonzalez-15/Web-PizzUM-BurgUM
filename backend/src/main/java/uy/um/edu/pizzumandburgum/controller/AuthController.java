package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.AuthRequest;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.AuthResponse;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.security.JwtUtil;
import uy.um.edu.pizzumandburgum.service.Interfaces.AdministradorService;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteService;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

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
            return ResponseEntity.ok(new AuthResponse(token, "CLIENTE", cliente));
        } catch (Exception e) {}

        try {
            AdministradorResponseDTO admin = administradorService.login(new AdministradorRequestDTO(email, contrasenia));
            String token = jwtUtil.generateToken(email, "ADMIN");
            return ResponseEntity.ok(new AuthResponse(token, "ADMIN", admin));
        } catch (Exception e) {}

        // Si ambos fallan
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
}
