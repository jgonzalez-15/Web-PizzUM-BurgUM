package uy.um.edu.pizzumandburgum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.update.MedioDePagoUpdateDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.MedioDePagoService;

import java.util.List;

@RestController
@RequestMapping("/api/medioDePago")
@PreAuthorize("hasAuthority('CLIENTE')")
@CrossOrigin(origins = "http://localhost:5173")
public class MedioDePagoController {

    @Autowired
    private MedioDePagoService medioDePagoService;

    @PostMapping("/agregar")
    public ResponseEntity<MedioDePagoDTO> aniadirMedioDePago(@RequestBody MedioDePagoRequestDTO request, Authentication authentication) {
        String idCliente = authentication.getName();
        MedioDePagoDTO response = medioDePagoService.aniadirMedioDePago(request,idCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<MedioDePagoDTO> editarMDP(@PathVariable  Long id, @RequestBody MedioDePagoUpdateDTO dto) {
        MedioDePagoDTO response = medioDePagoService.editarMDP(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<MedioDePagoDTO>> listarMediosPorCliente(Authentication authentication) {
        String email = authentication.getName();
        List<MedioDePagoDTO> medios = medioDePagoService.listarPorCliente(email);
        return ResponseEntity.ok(medios);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarMedioDePago(Authentication authentication, @PathVariable Long id) {
        String email = authentication.getName();
        medioDePagoService.eliminarMedioDePago(email, id);
        return ResponseEntity.noContent().build();
    }

}


