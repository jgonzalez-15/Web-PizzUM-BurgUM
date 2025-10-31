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
public class PizzaResponseDTO {
    private float precio;
    private long tamanio;
    private ClienteResponseDTO cliente;
    private List<PizzaProductoResponseDTO> ingredientes;

}
