package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaProductoResponseDTO;

public interface HamburguesaProductoService {
    HamburguesaProductoResponseDTO agregarIngrediente(Long idHamburguesa,HamburguesaProductoRequestDTO dto);
    float calcularPrecio(Long idhamburguesa);
}
