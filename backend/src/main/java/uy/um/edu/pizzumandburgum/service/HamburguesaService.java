package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;

public interface HamburguesaService {
    HamburguesaResponseDTO crearHamburguesa(Long idHamburguesa);
    float fijarPrecio(Long idHamburguesa);
}
