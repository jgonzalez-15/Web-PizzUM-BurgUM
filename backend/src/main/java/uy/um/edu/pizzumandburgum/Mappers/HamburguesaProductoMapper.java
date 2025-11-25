package uy.um.edu.pizzumandburgum.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.DTOs.Request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.HamburguesaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.Excepciones.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.Repositorios.ProductoRepository;

@Component
public class HamburguesaProductoMapper {

    @Autowired
    private ProductoRepository productoRepository;

    public HamburguesaProducto toEntity(HamburguesaProductoRequestDTO dto) {
        HamburguesaProducto hamburguesaProducto = new HamburguesaProducto();
        hamburguesaProducto.setProducto(productoRepository.findById(dto.getIdProducto()).orElseThrow(ProductoNoExisteException::new));
        hamburguesaProducto.setCantidad(dto.getCantidad());
        return hamburguesaProducto;
    }

    public HamburguesaProductoResponseDTO toResponseDTO(HamburguesaProducto hamburguesaProducto) {
        return new HamburguesaProductoResponseDTO(
                hamburguesaProducto.getHamburguesa().getId(),
                hamburguesaProducto.getProducto().getIdProducto(),
                hamburguesaProducto.getCantidad()
        );
    }
}
