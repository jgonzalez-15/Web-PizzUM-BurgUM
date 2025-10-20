package uy.um.edu.pizzumandburgum.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.HamburguesaProducto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HamburguesaRequestDTO {

    private int cantCarnes;
    private float precio;
    private boolean esFavorita;
    private List<Long> ingredientes;

}
