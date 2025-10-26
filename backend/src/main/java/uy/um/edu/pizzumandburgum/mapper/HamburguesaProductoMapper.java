package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;

@Component
public class HamburguesaProductoMapper {

    @Autowired
    private ProductoRepository productoRepository;
    public HamburguesaProducto toEntity(HamburguesaProductoRequestDTO dto, Hamburguesa hamburguesa) {
        HamburguesaProducto hamburguesaProducto = new HamburguesaProducto();
        hamburguesaProducto.setProducto(productoRepository.findById(dto.getIdProducto()).orElseThrow(()->new ProductoNoExisteException()));
        hamburguesaProducto.setHamburguesa(hamburguesa);
        hamburguesaProducto.setCantidad(dto.getCantidad());
        return hamburguesaProducto;
    }

    public HamburguesaProductoResponseDTO toResponseDTO(HamburguesaProducto hamburguesaProducto) {
        return new HamburguesaProductoResponseDTO(hamburguesaProducto.getHamburguesa().getId(),hamburguesaProducto.getProducto().getIdProducto(),hamburguesaProducto.getCantidad());
    }
}
