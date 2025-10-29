package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.PizzaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.PizzaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.PizzaNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.PizzaProductoMapper;
import uy.um.edu.pizzumandburgum.repository.PizzaProductoRepository;
import uy.um.edu.pizzumandburgum.repository.PizzaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.PizzaProductoService;

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
        Pizza pizza = pizzaRepository.findById(idPizza).orElseThrow(()-> new PizzaNoExisteException());
        pp.setPizza(pizza);
        pp.setProducto(productoRepository.findById(dto.getIdProducto()).orElseThrow(()-> new ProductoNoExisteException()));
        pp.setCantidad(dto.getCantidad());
        pizzaProductoRepository.save(pp);
        pizza.getIngredientes().add(pp);
        PizzaProductoResponseDTO retornar = pizzaProductoMapper.toResponseDTO(pp);
        return retornar;
    }

    @Override
    public float calcularPrecio(Long idPizza) {
        float precio = 0;
        Pizza pizza = pizzaRepository.findById(idPizza).orElseThrow(() -> new PizzaNoExisteException());

        for (PizzaProducto pp : pizza.getIngredientes()){
            precio += pp.getProducto().getPrecio() * pp.getCantidad();
        }

        return precio;
    }
}
