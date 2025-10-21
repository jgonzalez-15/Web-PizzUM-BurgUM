package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;

public interface PizzaService {
    PizzaResponseDTO crearPizza(Long idPizza) ;
    float fijarPrecio(Long idPizza);
}
