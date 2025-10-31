package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.update.MedioDePagoUpdateDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.MedioDePagoService;

@RestController
@RequestMapping("/api/mediodepago")
@CrossOrigin(origins = "http://localhost:5173")
public class MedidoDePagoController {
    @Autowired
    private MedioDePagoService medioDePagoService;

    @PostMapping("/añadir")
    public ResponseEntity<MedioDePagoDTO> aniadirMedioDePago(@RequestBody MedioDePagoRequestDTO request, @RequestBody String idCliente, HttpSession sesion) {
        String rol = (String) sesion.getAttribute("rol");

        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        MedioDePagoDTO response = medioDePagoService.aniadirMedioDePago(request, idCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PutMapping("/{email}/mdp")
    public ResponseEntity<MedioDePagoDTO> editarMDP(@PathVariable String email, @RequestBody MedioDePagoUpdateDTO dto) {
        MedioDePagoDTO response = medioDePagoService.editarMDP(email, dto);
        return ResponseEntity.ok(response);
    }
}
