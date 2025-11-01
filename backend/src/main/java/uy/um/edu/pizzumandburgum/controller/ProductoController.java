package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // para permitir peticiones desde React
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @PostMapping("/crearProducto")
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO dto,
                                                            HttpSession session) {
        String rol = (String) session.getAttribute("rol");

        if (rol == null || !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }

        ProductoResponseDTO nuevo = productoService.agregarProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    @DeleteMapping("/{idProducto}/eliminar")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long idProducto, HttpSession session ) {

        String rol = (String) session.getAttribute("rol");

        if (rol == null || !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        productoService.eliminarProducto(idProducto);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/modificar")
    public ResponseEntity<Void> modificarProducto(
            @RequestBody ProductoRequestDTO productoviejoDTO, @RequestBody ProductoRequestDTO productonuevoDTO, HttpSession session ) {
        String rol = (String) session.getAttribute("rol");

        if (rol == null || !rol.equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        productoService.modificarProducto(productoviejoDTO,productonuevoDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        List<ProductoResponseDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/bebidas")
    public ResponseEntity<List<ProductoResponseDTO>> listarBebida(){
        List<ProductoResponseDTO> productos = productoService.listarBebidas();
        return ResponseEntity.ok(productos);
    }
}
