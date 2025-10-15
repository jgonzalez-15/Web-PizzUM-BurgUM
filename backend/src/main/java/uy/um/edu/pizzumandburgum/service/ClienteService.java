package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;

public interface ClienteService {
    ClienteResponseDTO registrarCliente(ClienteRequestDTO dto);
}
