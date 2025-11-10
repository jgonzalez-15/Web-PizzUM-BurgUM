package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.repository.PizzaRepository;
import uy.um.edu.pizzumandburgum.repository.HamburguesaRepository;

@Service
public class ProductoCreacionUpdater {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private HamburguesaRepository hamburguesaRepository;

    public void actualizarPreciosPorProducto(Producto producto) {
        Long productoId = producto.getIdProducto();

        for (Pizza pizza : pizzaRepository.findAll()) {
            boolean usa = pizza.getIngredientes().stream()
                    .anyMatch(ing -> ing.getProducto() != null && productoId.equals(ing.getProducto().getIdProducto()));
            if (!usa) continue;

            double nuevoPrecioDouble = pizza.getIngredientes().stream()
                    .mapToDouble(ing -> {
                        double p = ing.getProducto() != null ? ing.getProducto().getPrecio() : 0d;
                        int qty = ing.getCantidad() != 0 ? ing.getCantidad() : 1;
                        return p * qty;
                    })
                    .sum();

            pizza.setPrecio((float) nuevoPrecioDouble);
            pizzaRepository.save(pizza);
        }

        for (Hamburguesa h : hamburguesaRepository.findAll()) {
            boolean usa = h.getIngredientes().stream()
                    .anyMatch(ing -> ing.getProducto() != null && productoId.equals(ing.getProducto().getIdProducto()));
            if (!usa) continue;

            double nuevoPrecioDouble = h.getIngredientes().stream()
                    .mapToDouble(ing -> {
                        double p = ing.getProducto() != null ? ing.getProducto().getPrecio() : 0d;
                        int qty = ing.getCantidad() != 0 ? ing.getCantidad() : 1;
                        return p * qty;
                    })
                    .sum();

            h.setPrecio((float) nuevoPrecioDouble);
            hamburguesaRepository.save(h);
        }
    }
}
