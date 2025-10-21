package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ClienteDomicilioRepository;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.ClienteDomicilioService;

@Service
public class ClienteDomicilioServiceImpl implements ClienteDomicilioService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private ClienteDomicilioRepository clienteDomicilioRepository;
    @Override
    public void agregarDomicilio(String idCliente, String idDomicilio) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new ClienteNoExisteException());
        Domicilio domicilio = domicilioRepository.findById(idDomicilio).orElseThrow(()-> new DomicilioNoExisteException());

        ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
        clienteDomicilio.setCliente(cliente);
        clienteDomicilio.setDomicilio(domicilio);

        clienteDomicilioRepository.save(clienteDomicilio);
    }

    @Override
    public Domicilio obtenerDomicilio(String clienteId, String direccion) {
        Cliente cliente = clienteRepository.findByEmail(clienteId);
        Domicilio domicilio = new Domicilio(direccion,null);
        domicilio.setDireccion(direccion);
        for (ClienteDomicilio d : cliente.getDomicilios() ){
            if (d.getDomicilio().equals(domicilio.getDireccion())){
               return domicilioRepository.findById(d.getDomicilio().getDireccion()).orElseThrow(() -> new DomicilioNoExisteException());
            }
        }
        return null;
    }
}
