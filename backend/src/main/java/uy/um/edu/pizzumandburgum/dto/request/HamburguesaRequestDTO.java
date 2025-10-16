package uy.um.edu.pizzumandburgum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;

import java.util.List;

@Getter
@Setter
public class HamburguesaRequestDTO {
    @NotBlank (message = "La cantidad de carnes es obligatoria")
    private int cantCarnes;
    private List<HamburguesaProducto> ingredientes;

    public HamburguesaRequestDTO() {
    }

    public HamburguesaRequestDTO(int cantCarnes, List<HamburguesaProducto> ingredientes) {
        this.cantCarnes = cantCarnes;
        this.ingredientes = ingredientes;
    }
}
