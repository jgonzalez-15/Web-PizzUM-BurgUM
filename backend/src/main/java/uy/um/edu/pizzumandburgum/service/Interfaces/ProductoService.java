package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO agregarProducto (ProductoRequestDTO productoDTO);
    void eliminarProducto (Long idProducto);
    void modificarProducto(ProductoRequestDTO productoviejoDTO, ProductoRequestDTO productonuevoDTO);
    List<ProductoResponseDTO> listarProductos();
    List<ProductoResponseDTO> listarBebidas();

}
