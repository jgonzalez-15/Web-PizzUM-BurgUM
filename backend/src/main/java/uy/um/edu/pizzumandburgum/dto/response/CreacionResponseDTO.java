package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreacionResponseDTO {
    private Long id;
    private float precio;
    private String tipo;
    private List<ProductoResponseDTO> ingredientes;
}
