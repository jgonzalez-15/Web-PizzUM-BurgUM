package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.PizzaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;

import java.util.List;

public interface PizzaService {
    PizzaResponseDTO crearPizza(PizzaRequestDTO dto) ;
    float fijarPrecio(Long idPizza);
    List<PizzaResponseDTO> listarPizzas();

    List<ProductoResponseDTO> obtenerIngredientesPizza(Long idCreacion);
}
