package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.HamburguesaProductoResponseDTO;

public interface HamburguesaProductoService {
    HamburguesaProductoResponseDTO agregarIngrediente(Long idHamburguesa,HamburguesaProductoRequestDTO dto);
    float calcularPrecio(Long idhamburguesa);
}
