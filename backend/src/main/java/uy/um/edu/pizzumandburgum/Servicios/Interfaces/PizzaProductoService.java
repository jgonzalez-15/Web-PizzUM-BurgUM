package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.PizzaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.PizzaProductoResponseDTO;

public interface PizzaProductoService {
    PizzaProductoResponseDTO agregarIngrediente(Long idPizza, PizzaProductoRequestDTO dto);
    float calcularPrecio(Long idPizza);
}
