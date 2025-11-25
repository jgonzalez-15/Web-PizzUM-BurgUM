package uy.um.edu.pizzumandburgum.DTOs.Request;

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
    private List<PizzaProductoRequestDTO> ingredientes;
}

