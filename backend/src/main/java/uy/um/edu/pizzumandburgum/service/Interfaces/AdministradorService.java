package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.AdministradorLoginRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.update.AdministradorUpdateDTO;

import java.util.List;

public interface AdministradorService {
    AdministradorResponseDTO agregarAdmin(AdministradorRequestDTO dto);
    AdministradorResponseDTO login(AdministradorLoginRequestDTO dto);
    AdministradorResponseDTO editarPerfil(String email, AdministradorUpdateDTO dto);
    List<AdministradorResponseDTO> listarAdministradores();
    AdministradorResponseDTO obtenerAdministrador(String email);
    void eliminarAdministrador(String email);
    void asociarDomicilio(Long idDomicilio,String email);
}
