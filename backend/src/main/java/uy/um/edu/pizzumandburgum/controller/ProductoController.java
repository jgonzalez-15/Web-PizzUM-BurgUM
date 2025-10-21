package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ProductoDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ProductoService;

import java.util.List;

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
    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminarProducto(@RequestBody ProductoDTO productoDTO) {
        productoService.eliminarProducto(productoDTO);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/modificar")
    public ResponseEntity<Void> modificarProducto(
            @RequestBody ProductoDTO productoviejoDTO,@RequestBody ProductoDTO productonuevoDTO ) {
        productoService.modificarProducto(productoviejoDTO,productonuevoDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoDTO>> listarProductos() {
        List<ProductoDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }
}
