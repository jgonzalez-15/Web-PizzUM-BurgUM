package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.PizzaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.repository.PizzaProductoRepository;
import uy.um.edu.pizzumandburgum.service.PizzaProductoService;

@Service
public class PizzaProductoServiceImpl implements PizzaProductoService {

    @Autowired
    private PizzaProductoRepository pizzaProductoRepository;

    @Override
    public void agregarIngrediente(Pizza pizza, Producto producto, int cantidad) {
        PizzaProducto pp = new PizzaProducto();
        pp.setPizza(pizza);
        pp.setProducto(producto);
        pp.setCantidad(cantidad);
        pizzaProductoRepository.save(pp);
    }

    @Override
    public float calcularPrecio(PizzaResponseDTO pizzaResponseDTO) {
        float precio = 0;

        for (PizzaProducto pp : pizzaResponseDTO.getIngredientes()){
            precio += pp.getProducto().getPrecio() * pp.getCantidad();
        }

        return precio;
    }
}
