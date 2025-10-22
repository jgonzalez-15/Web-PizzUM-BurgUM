package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.PagoDummyRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PagoDummyResponseDTO;

public interface PagoDummyService {
    PagoDummyResponseDTO procesarPago(PagoDummyRequestDTO request);
}
