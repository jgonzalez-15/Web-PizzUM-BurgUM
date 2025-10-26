package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;


public class ClienteDomicilioMapper {

    @Autowired
    private DomicilioRepository domicilioRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    public ClienteDomicilio toEntity(ClienteDomicilioRequestDTO dto){
        ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
        Domicilio domicilio = domicilioRepository.findById(dto.getIdDomicilio()).orElseThrow(()-> new DomicilioNoExisteException());
        clienteDomicilio.setDomicilio(domicilio);
        Cliente cliente = clienteRepository.findByEmail(dto.getEmail()).orElseThrow(()-> new ClienteNoExisteException());
        clienteDomicilio.setCliente(cliente);
        return clienteDomicilio;
    }

    public ClienteDomicilioResponseDTO toResponseDTO (ClienteDomicilio clienteDomicilio){
        return new ClienteDomicilioResponseDTO(clienteDomicilio.getId(),clienteDomicilio.getCliente().getEmail(),clienteDomicilio.getDomicilio().getId());
    }
}
