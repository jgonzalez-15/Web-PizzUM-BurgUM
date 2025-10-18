package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorDTO;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.Cliente;

@Component
public class AdministradorMapper {
    public Administrador toEntity(AdministradorDTO dto) {
        Administrador admin = new Administrador();
        admin.setNombre(dto.getNombre());
        admin.setApellido(dto.getApellido());
        admin.setEmail(dto.getEmail());
        admin.setContrasenia(dto.getContrasenia());
        admin.setFechaNac(dto.getFechaNac());
        admin.setTelefono(dto.getTelefono());
        return admin;
    }

    public AdministradorDTO toResponseDTO(Administrador admin) {
        return new AdministradorDTO(admin.getNombre(),admin.getApellido(), admin.getEmail(),admin.getContrasenia() , admin.getTelefono(), admin.getFechaNac());
    }
}
