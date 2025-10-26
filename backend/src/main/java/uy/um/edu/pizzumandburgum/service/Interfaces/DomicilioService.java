package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;

import java.util.List;

public interface DomicilioService {
    DomicilioResponseDTO crearDomicilio(DomicilioRequestDTO dto);

    List<DomicilioResponseDTO> listarDomicilios();
}
