package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;

public interface HamburguesaService {
    HamburguesaResponseDTO crearHamburguesa(HamburguesaResponseDTO hamburguesa);
    void fijarPrecio(Long idHamburguesa);
}
