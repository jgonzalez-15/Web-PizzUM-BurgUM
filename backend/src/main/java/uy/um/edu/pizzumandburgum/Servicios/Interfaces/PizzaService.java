package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.PizzaRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.PizzaResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ProductoResponseDTO;

import java.util.List;

public interface PizzaService {
    PizzaResponseDTO crearPizza(PizzaRequestDTO dto) ;
    float fijarPrecio(Long idPizza);
    List<PizzaResponseDTO> listarPizzas();
    List<ProductoResponseDTO> obtenerIngredientesPizza(Long idCreacion);
}
