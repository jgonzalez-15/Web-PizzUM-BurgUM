package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.AdministradorRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.exceptions.Domicilio.DomicilioNoExisteException;
import uy.um.edu.pizzumandburgum.repository.AdministradorRepository;
import uy.um.edu.pizzumandburgum.repository.DomicilioRepository;

@Component
public class AdministradorMapper {

    @Autowired
    private DomicilioRepository domicilioRepository;

    public Administrador toEntity(AdministradorRequestDTO dto) {
        Administrador admin = new Administrador();
        admin.setNombre(dto.getNombre());
        admin.setApellido(dto.getApellido());
        admin.setContrasenia(dto.getContrasenia());
        admin.setEmail(dto.getEmail());
        admin.setFechaNac(dto.getFechaNac());
        admin.setTelefono(dto.getTelefono());
        admin.setCedula(dto.getCedula());
        return admin;
    }

    public AdministradorResponseDTO toResponseDTO(Administrador admin) {
        String domicilio = "";
        if (admin.getDomicilio() != null) {
            domicilio = admin.getDomicilio().getDireccion();
        }
        return new AdministradorResponseDTO(admin.getEmail(), admin.getNombre(), admin.getApellido(), admin.getTelefono(), admin.getFechaNac(), admin.getCedula(), domicilio);
    }
}
