package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

import java.util.List;

public interface ClienteDomicilioService {
    void agregarDomicilio(ClienteDomicilioRequestDTO dto);
    Domicilio obtenerDomicilio(String clienteId, String direccion);
    List<DomicilioResponseDTO> listarDomiciliosDeCliente(String emailCliente);
    void eliminarDomicilioDeCliente(String emailCliente, Long idDomicilio);
    boolean clienteTieneDomicilio(String clienteId, Long domicilioId);
}
