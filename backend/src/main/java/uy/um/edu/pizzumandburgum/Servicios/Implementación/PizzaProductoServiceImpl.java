package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.DTOs.Request.PizzaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.PizzaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Pizza;
import uy.um.edu.pizzumandburgum.Entidades.PizzaProducto;
import uy.um.edu.pizzumandburgum.Excepciones.Creacion.Pizza.PizzaNoExisteException;
import uy.um.edu.pizzumandburgum.Excepciones.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.Mappers.PizzaProductoMapper;
import uy.um.edu.pizzumandburgum.Repositorios.PizzaProductoRepository;
import uy.um.edu.pizzumandburgum.Repositorios.PizzaRepository;
import uy.um.edu.pizzumandburgum.Repositorios.ProductoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.PizzaProductoService;

@Service
public class PizzaProductoServiceImpl implements PizzaProductoService {

    @Autowired
    private PizzaProductoRepository pizzaProductoRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PizzaProductoMapper pizzaProductoMapper;

    @Override
    public PizzaProductoResponseDTO agregarIngrediente(Long idPizza, PizzaProductoRequestDTO dto) {
        PizzaProducto pp = new PizzaProducto();
        Pizza pizza = pizzaRepository.findById(idPizza).orElseThrow(PizzaNoExisteException::new);
        pp.setPizza(pizza);
        pp.setProducto(productoRepository.findById(dto.getIdProducto()).orElseThrow(ProductoNoExisteException::new));
        pp.setCantidad(dto.getCantidad());
        pizzaProductoRepository.save(pp);
        pizza.getIngredientes().add(pp);
        PizzaProductoResponseDTO retornar = pizzaProductoMapper.toResponseDTO(pp);
        return retornar;
    }

    @Override
    public float calcularPrecio(Long idPizza) {
        float precio = 0;
        Pizza pizza = pizzaRepository.findById(idPizza).orElseThrow(PizzaNoExisteException::new);

        for (PizzaProducto pp : pizza.getIngredientes()){
            precio += pp.getProducto().getPrecio() * pp.getCantidad();
        }

        return precio;
    }
}
