package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.repository.ClienteDomicilioRepository;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.service.ClienteDomicilioService;

@Service
public class ClienteDomicilioServiceImpl implements ClienteDomicilioService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteDomicilioRepository clienteDomicilioRepository;
    @Override
    public void agregarDomicilio(Cliente cliente, Domicilio domicilio) {
        ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
        clienteDomicilio.setCliente(cliente);
        clienteDomicilio.setDomicilio(domicilio);

        clienteDomicilioRepository.save(clienteDomicilio);
    }

    @Override
    public Domicilio obtenerDomicilio(String clienteId, String direccion) {
        Cliente cliente = clienteRepository.findByEmail(clienteId);
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion(direccion);
        for (ClienteDomicilio d : cliente.getDomicilios() ){
            if (d.getDomicilio().equals(domicilio)){
                return d.getDomicilio();
            }
        }
        return null;
    }
}
