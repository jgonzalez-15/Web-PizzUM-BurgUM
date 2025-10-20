package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.response.PizzaResponseDTO;

public interface PizzaService {
    PizzaResponseDTO crearPizza(Long idPizza) ;
    float fijarPrecio(Long idPizza);
}
