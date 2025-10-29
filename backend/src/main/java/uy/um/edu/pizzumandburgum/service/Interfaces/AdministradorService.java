package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;

public interface AdministradorService {
    AdministradorResponseDTO agregarAdmin(AdministradorResponseDTO dto);
    AdministradorResponseDTO login(AdministradorRequestDTO dto);
}
