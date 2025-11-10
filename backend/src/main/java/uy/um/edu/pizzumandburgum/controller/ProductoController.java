package uy.um.edu.pizzumandburgum.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uy.um.edu.pizzumandburgum.dto.request.ProductoModificarRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.service.Interfaces.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/crearProducto")
    public ResponseEntity<ProductoResponseDTO> crearProducto(@RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO nuevo = productoService.agregarProducto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{idProducto}/eliminar")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long idProducto) {
        productoService.eliminarProducto(idProducto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('CLIENTE') or hasAuthority('ADMIN')")
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {
        List<ProductoResponseDTO> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    @PreAuthorize("hasAuthority('CLIENTE') or hasAuthority('ADMIN')")
    @GetMapping("/bebidas")
    public ResponseEntity<List<ProductoResponseDTO>> listarBebida(){
        List<ProductoResponseDTO> productos = productoService.listarBebidas();
        return ResponseEntity.ok(productos);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/ocultar")
    public ResponseEntity<Void> ocultarProducto(@PathVariable Long id) {
        productoService.ocultarProducto(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/mostrar")
    public ResponseEntity<Void> mostrarProducto(@PathVariable Long id) {
        productoService.mostrarProducto(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/listarAdmin")
    public ResponseEntity<List<ProductoResponseDTO>> listarAdmin() {
        return ResponseEntity.ok(productoService.listarProductosAdmin());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/editar")
    public ResponseEntity<ProductoResponseDTO> editarProducto(
            @PathVariable Long id,
            @RequestBody ProductoRequestDTO dto) {

        ProductoResponseDTO actualizado = productoService.editarProducto(id, dto);
        return ResponseEntity.ok(actualizado);
    }

}
