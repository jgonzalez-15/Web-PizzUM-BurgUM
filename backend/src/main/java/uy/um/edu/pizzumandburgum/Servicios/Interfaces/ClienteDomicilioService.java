package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Domicilio;

import java.util.List;

public interface ClienteDomicilioService {
    void agregarDomicilio(ClienteDomicilioRequestDTO dto);
    Domicilio obtenerDomicilio(String clienteId, String direccion);
    List<DomicilioResponseDTO> listarDomiciliosDeCliente(String emailCliente);
    void eliminarDomicilioDeCliente(String emailCliente, Long idDomicilio);
    boolean clienteTieneDomicilio(String clienteId, Long domicilioId);
}
