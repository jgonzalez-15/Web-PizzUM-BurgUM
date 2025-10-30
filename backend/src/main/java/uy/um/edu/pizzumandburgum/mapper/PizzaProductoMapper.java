package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.PizzaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.PizzaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.PizzaProducto;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.repository.PizzaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;

@Component
public class PizzaProductoMapper {

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private ProductoRepository productoRepository;

    public PizzaProductoResponseDTO toResponseDTO(PizzaProducto pizzaProducto){
        return new PizzaProductoResponseDTO(pizzaProducto.getCantidad(),pizzaProducto.getPizza().getId(),productoMapper.toResponseDTO(pizzaProducto.getProducto()));
    }

    public PizzaProducto toEntity (PizzaProductoRequestDTO dto){
        PizzaProducto pizzaProducto = new PizzaProducto();
        pizzaProducto.setProducto(productoRepository.findById(dto.getIdProducto()).orElseThrow(()-> new ProductoNoExisteException()));
        pizzaProducto.setCantidad(dto.getCantidad());
        return pizzaProducto;
    }
}
