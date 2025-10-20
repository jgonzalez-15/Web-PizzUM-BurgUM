package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

public interface ClienteDomicilioService {
    void agregarDomicilio(String idCliente, String idDomicilio);
    Domicilio obtenerDomicilio(String clienteId, String direccion);
}
