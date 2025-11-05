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

import java.util.List;

@RestController
@RequestMapping("/api/medioDePago")
@CrossOrigin(origins = "http://localhost:5173")
public class MedioDePagoController {

    @Autowired
    private MedioDePagoService medioDePagoService;


    @PostMapping("/{idCliente}/agregar")
    public ResponseEntity<MedioDePagoDTO> aniadirMedioDePago(@RequestBody MedioDePagoRequestDTO request, @PathVariable String idCliente) {
        MedioDePagoDTO response = medioDePagoService.aniadirMedioDePago(request,idCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/mdp")
    public ResponseEntity<MedioDePagoDTO> editarMDP(@PathVariable  Long id, @RequestBody MedioDePagoUpdateDTO dto, HttpSession sesion) {
        String rol = (String) sesion.getAttribute("rol");
        if (rol == null || !rol.equals("CLIENTE")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        MedioDePagoDTO response = medioDePagoService.editarMDP(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{email}/listar")
    public ResponseEntity<List<MedioDePagoDTO>> listarMediosPorCliente(@PathVariable String email) {
        List<MedioDePagoDTO> medios = medioDePagoService.listarPorCliente(email);
        return ResponseEntity.ok(medios);
    }

    @DeleteMapping("/eliminar/{email}/{id}")
    public ResponseEntity<Void> eliminarMedioDePago(@PathVariable String email, @PathVariable Long id) {
        medioDePagoService.eliminarMedioDePago(email, id);
        return ResponseEntity.noContent().build();
    }

}


