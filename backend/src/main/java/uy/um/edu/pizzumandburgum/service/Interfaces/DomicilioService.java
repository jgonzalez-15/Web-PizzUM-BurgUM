package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

public interface DomicilioService {
    public DomicilioResponseDTO crearDomicilio(DomicilioRequestDTO dto);
    //void modificarDomicilio(DomicilioRequestDTO dto);
}
