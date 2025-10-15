package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;

@Component
public class ClienteMapper {
    public Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setPassword(dto.getPassword());
        cliente.setFechaNac(dto.getFechaNacimiento());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }

    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getEmail(),cliente.getNombre(),cliente.getApellido(),cliente.getTelefono(),cliente.getFechaNac());
    }

}
