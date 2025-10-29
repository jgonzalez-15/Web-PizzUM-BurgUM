package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Hamburguesa.HamburguesaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.repository.HamburguesaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;

@Component
public class HamburguesaProductoMapper {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    public HamburguesaProducto toEntity(HamburguesaProductoRequestDTO dto) {
        HamburguesaProducto hamburguesaProducto = new HamburguesaProducto();
        hamburguesaProducto.setProducto(productoRepository.findById(dto.getIdProducto()).orElseThrow(()->new ProductoNoExisteException()));
        hamburguesaProducto.setCantidad(dto.getCantidad());
        return hamburguesaProducto;
    }

    public HamburguesaProductoResponseDTO toResponseDTO(HamburguesaProducto hamburguesaProducto) {
        return new HamburguesaProductoResponseDTO(hamburguesaProducto.getHamburguesa().getId(),hamburguesaProducto.getProducto().getIdProducto(),hamburguesaProducto.getCantidad());
    }
}
