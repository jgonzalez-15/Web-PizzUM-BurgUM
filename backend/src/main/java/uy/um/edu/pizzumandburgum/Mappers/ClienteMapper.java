package uy.um.edu.pizzumandburgum.Mappers;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.DTOs.Request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;

@Component
public class ClienteMapper {
    public Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setEmail(dto.getEmail());
        cliente.setContrasenia(dto.getContrasenia());
        return cliente;
    }

    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getEmail(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getTelefono(),
                cliente.getFechaNac(),
                cliente.getCedula()
        );
    }

}
