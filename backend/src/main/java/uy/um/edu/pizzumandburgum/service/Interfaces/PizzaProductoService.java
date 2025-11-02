package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.PizzaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaProductoResponseDTO;

public interface PizzaProductoService {
    PizzaProductoResponseDTO agregarIngrediente(Long idPizza, PizzaProductoRequestDTO dto);
    float calcularPrecio(Long idPizza);
}
