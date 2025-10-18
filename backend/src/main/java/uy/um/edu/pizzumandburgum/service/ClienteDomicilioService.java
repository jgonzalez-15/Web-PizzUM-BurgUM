package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

public interface ClienteDomicilioService {
    void agregarDomicilio(Cliente cliente, Domicilio domicilio);
    Domicilio obtenerDomicilio(String clienteId, String direccion);
}
