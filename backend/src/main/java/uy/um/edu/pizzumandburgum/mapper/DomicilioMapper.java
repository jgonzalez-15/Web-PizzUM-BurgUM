package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

@Component
public class DomicilioMapper {
    public Domicilio toEntity(DomicilioRequestDTO dto) {
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion(dto.getDireccion());
        domicilio.setPedidos(dto.getPedidos());
        return domicilio;
    }

    public DomicilioRequestDTO toResponseDTO(Domicilio domicilio) {
        return new DomicilioRequestDTO(domicilio.getDireccion(),domicilio.getPedidos());
    }
}
