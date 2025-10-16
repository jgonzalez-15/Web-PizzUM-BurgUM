package uy.um.edu.pizzumandburgum.dto.response;

import lombok.Getter;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;
import uy.um.edu.pizzumandburgum.entities.Producto;

import java.util.List;

@Getter
@Setter
public class HamburguesaResponseDTO {
    private int cantCarnes;
    private List<HamburguesaProducto> ingredientes;

    public HamburguesaResponseDTO() {
    }

    public HamburguesaResponseDTO(int cantCarnes, List<HamburguesaProducto> ingredientes) {
        this.cantCarnes = cantCarnes;
        this.productos = ingredientes;
    }
}
