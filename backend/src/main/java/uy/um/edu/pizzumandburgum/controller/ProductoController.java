package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ProductoDTO;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.service.ProductoService;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "http://localhost:5173") // para permitir peticiones desde React
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/crearProducto")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO dto,
                                                     HttpSession session) {
        String rol = (String) session.getAttribute("rol");

        if (rol == null || !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }

        ProductoDTO nuevo = productoService.agregarProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
}
