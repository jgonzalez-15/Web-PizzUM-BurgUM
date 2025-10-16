package uy.um.edu.pizzumandburgum.mapper;

import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;

public class HamburguesaProductoMapper {
    public HamburguesaProducto toEntity(HamburguesaProductoRequestDTO dto) {
        HamburguesaProducto hamburguesa = new HamburguesaProducto();
        dto.setHamburguesa(dto.getHamburguesa());
        dto.setProducto(dto.getProducto());
        dto.setCantidad(dto.getCantidad());
        return hamburguesa;
    }

    public HamburguesaProductoRequestDTO toResponseDTO(HamburguesaProducto hamburguesaProducto) {
        return new HamburguesaProductoRequestDTO(hamburguesaProducto.getHamburguesa(),hamburguesaProducto.getProducto(),hamburguesaProducto.getCantidad());
    }
}
