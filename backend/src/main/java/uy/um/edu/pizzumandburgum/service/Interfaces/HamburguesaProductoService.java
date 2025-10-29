package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.HamburguesaProductoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaProductoResponseDTO;

public interface HamburguesaProductoService {
    public HamburguesaProductoResponseDTO agregarIngrediente(Long idHamburguesa,HamburguesaProductoRequestDTO dto);
    public float calcularPrecio(Long idhamburguesa);
}
