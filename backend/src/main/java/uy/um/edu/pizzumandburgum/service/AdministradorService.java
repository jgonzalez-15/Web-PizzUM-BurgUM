package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.request.AdministradorDTO;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;

public interface AdministradorService {
    AdministradorDTO agregarAdmin(AdministradorDTO dto);
    AdministradorDTO login(String email, String contrasenia);
}
