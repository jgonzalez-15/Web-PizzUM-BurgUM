package uy.um.edu.pizzumandburgum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaProductoRequestDTO {
    private Long idProducto;
    private int cantidad;
}
