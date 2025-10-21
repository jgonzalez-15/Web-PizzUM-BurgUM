package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.Producto;

public interface PizzaProductoService {
    public void agregarIngrediente(Pizza pizza, Producto producto, int cantidad);
    public float calcularPrecio(Long idPizza);
}
