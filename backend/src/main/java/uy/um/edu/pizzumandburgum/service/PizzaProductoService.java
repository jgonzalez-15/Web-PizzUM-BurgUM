package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.Producto;

public interface PizzaProductoService {
    public void agregarIngrediente(Pizza pizza, Producto producto, int cantidad);
    public float calcularPrecio(PizzaResponseDTO pizzaResponseDTO);
}
