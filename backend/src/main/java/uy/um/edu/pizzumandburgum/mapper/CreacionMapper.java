package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.CreacionResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Creacion;

@Component
public class CreacionMapper {
    public CreacionResponseDTO toResponseDTO(Creacion creacion) {
        if (creacion == null) {
            return null;
        }

        return new CreacionResponseDTO(
                creacion.getId(),
                creacion.getPrecio(),
                creacion.isEsFavorita()
        );
    }
}
