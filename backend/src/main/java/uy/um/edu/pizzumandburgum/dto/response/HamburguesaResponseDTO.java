package uy.um.edu.pizzumandburgum.dto.response;

import lombok.Getter;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Producto;

import java.util.List;

@Getter
@Setter
public class HamburguesaResponseDTO {
    private int cantCarnes;
    private List<Producto> productos;

    public HamburguesaResponseDTO() {
    }

    public HamburguesaResponseDTO(int cantCarnes, List<Producto> productos) {
        this.cantCarnes = cantCarnes;
        this.productos = productos;
    }
}
