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
    public ClienteDomicilioResponseDTO agregarDomicilio(ClienteDomicilioRequestDTO clienteDomicilioRequestDTO) {
        Cliente cliente = clienteRepository.findById(clienteDomicilioRequestDTO.getEmailCliente()).orElseThrow(ClienteNoExisteException::new);
        Domicilio domicilio = domicilioRepository.findById(clienteDomicilioRequestDTO.getIdDomicilio()).orElseThrow(DomicilioNoExisteException::new);

        ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
        clienteDomicilio.setCliente(cliente);
        clienteDomicilio.setDomicilio(domicilio);

        ClienteDomicilio guardado = clienteDomicilioRepository.save(clienteDomicilio);
        return clienteDomicilioMapper.toResponseDTO(guardado);
    }


    @Override
    public Domicilio obtenerDomicilio(String clienteId, Long idDomicilio) {
        Cliente cliente = clienteRepository.findByEmail(clienteId);
        if (cliente == null) {
            throw new ClienteNoExisteException();
        }

        for (ClienteDomicilio cd : cliente.getDomicilios()) {
            if (cd.getDomicilio().getId() == idDomicilio) {
                return domicilioRepository.findById(idDomicilio).orElseThrow(DomicilioNoExisteException::new);
            }
        }

        throw new DomicilioNoExisteException();
    }
}
