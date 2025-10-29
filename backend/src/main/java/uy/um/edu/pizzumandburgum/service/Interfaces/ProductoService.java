package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.ProductoDTO;

import java.util.List;

public interface ProductoService {
    ProductoDTO agregarProducto (ProductoDTO productoDTO);
    void eliminarProducto (ProductoDTO productoDTO);
    void modificarProducto(ProductoDTO productoviejoDTO, ProductoDTO productonuevoDTO);
    List<ProductoDTO> listarProductos();
    List<ProductoDTO> listarBebidas();

}
