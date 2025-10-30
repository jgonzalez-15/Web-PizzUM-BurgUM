package uy.um.edu.pizzumandburgum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaRequestDTO {
    private String clienteId;
    private boolean esFavorita;
    private long tamanio;
    private List<PizzaProductoRequestDTO> ingredientes;
}

