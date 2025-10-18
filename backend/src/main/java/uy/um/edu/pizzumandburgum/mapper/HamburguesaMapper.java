package uy.um.edu.pizzumandburgum.mapper;


import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;

@Component
public class HamburguesaMapper {
    public Hamburguesa toEntity(HamburguesaResponseDTO dto) {
        Hamburguesa hamburguesa = new Hamburguesa();
        dto.setCantCarnes(dto.getCantCarnes());
        return hamburguesa;
    }

    public HamburguesaResponseDTO toResponseDTO(Hamburguesa hamburguesa) {
        return new HamburguesaResponseDTO(hamburguesa.getCantCarnes(),hamburguesa.getPrecio(),hamburguesa.isEsFavorita(),hamburguesa.getIngredientes());
    }
}
