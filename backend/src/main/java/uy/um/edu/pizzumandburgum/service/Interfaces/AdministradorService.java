package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.AdministradorDTO;

public interface AdministradorService {
    AdministradorDTO agregarAdmin(AdministradorDTO dto);
    AdministradorDTO login(String email, String contrasenia);
}
