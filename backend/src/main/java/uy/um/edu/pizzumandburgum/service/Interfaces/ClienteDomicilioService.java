package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

public interface ClienteDomicilioService {
    ClienteDomicilioResponseDTO agregarDomicilio(ClienteDomicilioRequestDTO dto);
    Domicilio obtenerDomicilio(String clienteId, String direccion);
}
