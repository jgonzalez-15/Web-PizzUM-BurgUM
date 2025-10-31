package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaProductoResponseDTO {
    private int cantidad; // número de unidades de ese producto
    private Long pizza;
    private ProductoResponseDTO producto;
}
