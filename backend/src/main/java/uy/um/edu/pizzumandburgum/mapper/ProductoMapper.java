package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Producto;

@Component
public class ProductoMapper {
    public Producto toEntity(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setPrecio(dto.getPrecio());
        producto.setTipo(dto.getTipo());
        producto.setNombre(dto.getNombre());
        producto.setSinTacc(dto.isSinTacc());
        return producto;
    }

    public ProductoResponseDTO toResponseDTO(Producto producto) {
        return new ProductoResponseDTO(producto.getIdProducto(),producto.getTipo(),producto.getNombre(),producto.isSinTacc(),producto.getPrecio());
    }
}
