package uy.um.edu.pizzumandburgum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HamburguesaRequestDTO {
    @NotBlank (message = "La cantidad de carnes es obligatoria")
    private int cantCarnes;

    public HamburguesaRequestDTO() {
    }

    public HamburguesaRequestDTO(int cantCarnes) {
        this.cantCarnes = cantCarnes;
    }
}
