package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;

@Component
public class AdministradorMapper {
    public Administrador toEntity(AdministradorResponseDTO dto) {
        Administrador admin = new Administrador();
        admin.setNombre(dto.getNombre());
        admin.setApellido(dto.getApellido());
        admin.setEmail(dto.getEmail());
        admin.setContrasenia(dto.getContrasenia());
        admin.setFechaNac(dto.getFechaNac());
        admin.setTelefono(dto.getTelefono());
        return admin;
    }

    public AdministradorResponseDTO toResponseDTO(Administrador admin) {
        return new AdministradorResponseDTO(admin.getEmail(), admin.getNombre(), admin.getApellido(), admin.getContrasenia(), admin.getTelefono(), admin.getFechaNac(), admin.getCedula(), admin.getDomicilio());
    }
}
