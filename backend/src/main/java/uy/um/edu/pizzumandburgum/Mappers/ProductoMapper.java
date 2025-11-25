package uy.um.edu.pizzumandburgum.Mappers;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.DTOs.Request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ProductoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Producto;

@Component
public class ProductoMapper {
    public Producto toEntity(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setPrecio(dto.getPrecio());
        producto.setTipo(dto.getTipo());
        producto.setNombre(dto.getNombre());
        producto.setSinTacc(dto.isSinTacc());
        producto.setVisible(dto.isVisible());
        return producto;
    }

    public ProductoResponseDTO toResponseDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getIdProducto(),
                producto.getTipo(),
                producto.getNombre(),
                producto.isSinTacc(),
                producto.getPrecio(),
                producto.isEstaActivo(),
                producto.isVisible()
        );
    }
}
