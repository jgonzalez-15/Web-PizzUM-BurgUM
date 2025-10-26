package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.PizzaNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.SinMasaException;
import uy.um.edu.pizzumandburgum.exceptions.Producto.ProductoNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.PizzaMapper;
import uy.um.edu.pizzumandburgum.repository.PizzaProductoRepository;
import uy.um.edu.pizzumandburgum.repository.PizzaRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.PizzaProductoService;
import uy.um.edu.pizzumandburgum.service.Interfaces.PizzaService;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaProductoService pizzaProductoService;

    @Autowired
    private PizzaMapper pizzaMapper;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PizzaProductoRepository pizzaProductoRepository;


    @Override
    public PizzaResponseDTO crearPizza(Long idPizza) {

        Pizza pizza = pizzaRepository.findById(idPizza).orElseThrow(() -> new PizzaNoExisteException());
        boolean tieneMasa = false;

        for (PizzaProducto pp: pizza.getIngredientes()){
            Producto p = pp.getProducto();
            if ("Masa".equalsIgnoreCase(p.getTipo())){
                tieneMasa = true;
            }
        }
        if (!tieneMasa){
            throw new SinMasaException();
        }
        float precio = this.fijarPrecio(pizza.getId());
        pizza.setPrecio(precio);
        Pizza guardado = pizzaRepository.save(pizza);

        // Delegamos la creación de ingredientes al service especializado
        for (PizzaProducto pp : pizza.getIngredientes()) {
            Producto producto = productoRepository.findById(pp.getProducto().getIdProducto()).orElseThrow(()->new ProductoNoExisteException());
            pizzaProductoService.agregarIngrediente(
                    guardado,
                    pp.getProducto(),
                    pp.getCantidad()
            );
        }
        return pizzaMapper.toResponseDTO(guardado);
    }

    @Override
    public float fijarPrecio(Long idPizza) {
        Pizza pizza = pizzaRepository.findById(String.valueOf(idPizza))
                .orElseThrow(() -> new PizzaNoExisteException());

        float precioTotal = pizzaProductoService.calcularPrecio(idPizza);
        pizza.setPrecio(precioTotal);
        pizzaRepository.save(pizza);

        return precioTotal;
    }

    @Override
    public List<PizzaResponseDTO> listarPizzas() {
        List<Pizza>pizzas = pizzaRepository.findAll();
        List<PizzaResponseDTO> retornar = new ArrayList<>();

        for(Pizza pizza: pizzas){
            retornar.add(pizzaMapper.toResponseDTO(pizza));
        }
        return retornar;
    }
}
