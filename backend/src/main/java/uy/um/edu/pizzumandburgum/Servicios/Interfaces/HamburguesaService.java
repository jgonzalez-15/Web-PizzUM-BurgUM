package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ProductoResponseDTO;

import java.util.List;

public interface HamburguesaService {
    HamburguesaResponseDTO crearHamburguesa(HamburguesaRequestDTO hamburguesaRequestDTO);
    float fijarPrecio(Long idHamburguesa);
    List<HamburguesaResponseDTO> listarHamburguesas();
    List<ProductoResponseDTO> obtenerIngredientesHamburguesa(Long idCreacion);
}
