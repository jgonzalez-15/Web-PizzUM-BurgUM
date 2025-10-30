package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.ClienteDomicilioMapper;
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

    @Autowired
    private ClienteDomicilioMapper clienteDomicilioMapper;
    @Override
    public ClienteDomicilioResponseDTO agregarDomicilio(ClienteDomicilioRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getEmail()).orElseThrow(() -> new ClienteNoExisteException());
        Domicilio domicilio = domicilioRepository.findById(dto.getIdDomicilio()).orElseThrow(()-> new DomicilioNoExisteException());

        ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
        clienteDomicilio.setCliente(cliente);
        clienteDomicilio.setDomicilio(domicilio);

        cliente.getDomicilios().add(clienteDomicilio);
        domicilio.getClientes().add(clienteDomicilio);

        clienteDomicilioRepository.save(clienteDomicilio);

        return clienteDomicilioMapper.toResponseDTO(clienteDomicilio);
    }

    @Override
    public Domicilio obtenerDomicilio(String clienteId, String direccion) {
        Cliente cliente = clienteRepository.findByEmail(clienteId).orElseThrow(() -> new ClienteNoExisteException());
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion(direccion);
        for (ClienteDomicilio d : cliente.getDomicilios() ){
            if (d.getDomicilio().equals(domicilio.getDireccion())){
               return domicilioRepository.findById(d.getDomicilio().getDireccion()).orElseThrow(() -> new DomicilioNoExisteException());
            }
        }
        return null;
    }
}
