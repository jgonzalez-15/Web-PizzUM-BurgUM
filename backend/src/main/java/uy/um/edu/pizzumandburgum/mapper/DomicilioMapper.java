package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

@Component
public class DomicilioMapper {
    public Domicilio toEntity(DomicilioRequestDTO dto) {
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(dto.getCalle());
        domicilio.setNumero(dto.getNumero());
        domicilio.setCiudad(dto.getCiudad());
        domicilio.setDepartamento(dto.getDepartamento());
        domicilio.setCodigoPostal(dto.getCodigoPostal());
        return domicilio;
    }

    public DomicilioResponseDTO toResponseDTO(Domicilio domicilio) {
        DomicilioResponseDTO dto = new DomicilioResponseDTO();
        dto.setId(domicilio.getId());
        dto.setCalle(domicilio.getCalle());
        dto.setNumero(domicilio.getNumero());
        dto.setCiudad(domicilio.getCiudad());
        dto.setDepartamento(domicilio.getDepartamento());
        dto.setCodigoPostal(domicilio.getCodigoPostal());
        return dto;
    }
}
