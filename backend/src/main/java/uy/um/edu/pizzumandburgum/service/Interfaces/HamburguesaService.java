package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.HamburguesaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;

import java.util.List;

public interface HamburguesaService {
    HamburguesaResponseDTO crearHamburguesa(HamburguesaRequestDTO hamburguesaRequestDTO);
    float fijarPrecio(Long idHamburguesa);
    List<HamburguesaResponseDTO> listarHamburguesas();

}
