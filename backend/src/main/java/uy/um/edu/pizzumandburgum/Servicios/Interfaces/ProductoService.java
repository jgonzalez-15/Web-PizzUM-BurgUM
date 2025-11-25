package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO agregarProducto (ProductoRequestDTO productoDTO);
    void eliminarProducto (Long idProducto);
    void ocultarProducto(Long idProducto);
    void mostrarProducto(Long idProducto);
    List<ProductoResponseDTO> listarProductos();
    List<ProductoResponseDTO> listarProductosAdmin();
    List<ProductoResponseDTO> listarBebidas();
    ProductoResponseDTO editarProducto(Long idProducto, ProductoRequestDTO dto);
}
