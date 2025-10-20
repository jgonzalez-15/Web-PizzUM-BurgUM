package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;

@Component
public class HamburguesaProductoMapper {
    public HamburguesaProducto toEntity(HamburguesaProductoRequestDTO dto) {
        HamburguesaProducto hamburguesa = new HamburguesaProducto();
        dto.setIdProducto(dto.getIdProducto());
        dto.setIdHamburguesa(dto.getIdHamburguesa());
        dto.setCantidad(dto.getCantidad());
        return hamburguesa;
    }

    public HamburguesaProductoRequestDTO toResponseDTO(HamburguesaProducto hamburguesaProducto) {
        return new HamburguesaProductoRequestDTO(hamburguesaProducto.getHamburguesa().getIdCreacion(),hamburguesaProducto.getProducto().getIdProducto(),hamburguesaProducto.getCantidad());
    }
}
