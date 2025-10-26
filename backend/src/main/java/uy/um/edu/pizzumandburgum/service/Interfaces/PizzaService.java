package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;

import java.util.List;

public interface PizzaService {
    PizzaResponseDTO crearPizza(Long idPizza) ;
    float fijarPrecio(Long idPizza);
    List<PizzaResponseDTO> listarPizzas();
}
