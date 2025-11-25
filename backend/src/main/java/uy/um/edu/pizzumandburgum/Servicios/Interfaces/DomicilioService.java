package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Update.DomicilioUpdateDTO;

public interface DomicilioService {
    DomicilioResponseDTO crearDomicilio(DomicilioRequestDTO dto);
    DomicilioResponseDTO editarPerfil(Long idDomicilio, DomicilioUpdateDTO dto);
    void eliminarDomicilio(Long domicilioId, String clienteId);

}
