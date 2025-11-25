package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.AdministradorLoginRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Update.AdministradorUpdateDTO;

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
