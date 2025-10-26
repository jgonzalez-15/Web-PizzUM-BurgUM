package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.PizzaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.Pizza.PizzaNoExisteException;
import uy.um.edu.pizzumandburgum.repository.PizzaProductoRepository;
import uy.um.edu.pizzumandburgum.repository.PizzaRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.PizzaProductoService;

@Service
public class PizzaProductoServiceImpl implements PizzaProductoService {

    @Autowired
    private PizzaProductoRepository pizzaProductoRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public void agregarIngrediente(Pizza pizza, Producto producto, int cantidad) {
        PizzaProducto pp = new PizzaProducto();
        pp.setPizza(pizza);
        pp.setProducto(producto);
        pp.setCantidad(cantidad);
        pizzaProductoRepository.save(pp);
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
