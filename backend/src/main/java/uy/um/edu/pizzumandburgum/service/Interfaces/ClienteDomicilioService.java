package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.entities.Domicilio;

public interface ClienteDomicilioService {
    void agregarDomicilio(String idCliente, Long idDomicilio);
    Domicilio obtenerDomicilio(String clienteId, String direccion);
}
