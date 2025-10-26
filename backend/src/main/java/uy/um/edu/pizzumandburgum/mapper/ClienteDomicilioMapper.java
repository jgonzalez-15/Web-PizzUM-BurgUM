package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.ClienteDomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteDomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

@Component
public class ClienteDomicilioMapper {
    public ClienteDomicilio toEntity(ClienteDomicilioRequestDTO dto, Cliente cliente, Domicilio domicilio) {
        ClienteDomicilio cd = new ClienteDomicilio();
        cd.setCliente(cliente);
        cd.setDomicilio(domicilio);
        return cd;
    }

    public ClienteDomicilioResponseDTO toResponseDTO(ClienteDomicilio entity) {
        ClienteDomicilioResponseDTO dto = new ClienteDomicilioResponseDTO();
        dto.setEmailCliente(entity.getCliente().getEmail());
        dto.setIdDomicilio(entity.getDomicilio().getId());
        return dto;
    }
}
