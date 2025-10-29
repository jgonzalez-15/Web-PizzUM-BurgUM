package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.PizzaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaProductoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pizza;
import uy.um.edu.pizzumandburgum.entities.Producto;

public interface PizzaProductoService {
    public PizzaProductoResponseDTO agregarIngrediente(Long idPizza, PizzaProductoRequestDTO dto);
    public float calcularPrecio(Long idPizza);
}
