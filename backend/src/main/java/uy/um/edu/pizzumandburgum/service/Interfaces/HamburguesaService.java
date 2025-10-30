package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;

import java.util.List;

public interface HamburguesaService {
    HamburguesaResponseDTO crearHamburguesa(HamburguesaRequestDTO hamburguesaRequestDTO);
    float fijarPrecio(Long idHamburguesa);
    List<HamburguesaResponseDTO> listarHamburguesas();
    List<ProductoResponseDTO> obtenerIngredientesHamburguesa(Long idCreacion);
}
