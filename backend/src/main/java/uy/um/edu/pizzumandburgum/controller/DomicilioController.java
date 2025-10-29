package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.AdministradorUpdateDTO;
import uy.um.edu.pizzumandburgum.dto.update.DomicilioUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.mapper.DomicilioMapper;
import uy.um.edu.pizzumandburgum.service.Interfaces.DomicilioService;

@RestController
@RequestMapping("/api/domicilio")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class DomicilioController {
    @Autowired
    private DomicilioService domicilioService;

    @Autowired
   private DomicilioMapper domicilioMapper;

    @PostMapping("/crearDomicilio")
    public ResponseEntity<DomicilioResponseDTO> crearDomicilio(@RequestBody DomicilioRequestDTO request, HttpSession sesion) {
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }

        DomicilioResponseDTO response = domicilioService.crearDomicilio(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{email}/domicilio")
    public ResponseEntity<DomicilioResponseDTO> editarDomicilio(
            @PathVariable Long idDomicilio,
            @RequestBody DomicilioUpdateDTO dto) {
        DomicilioResponseDTO response = domicilioService.editarPerfil(idDomicilio, dto);
        return ResponseEntity.ok(response);
    }
}
