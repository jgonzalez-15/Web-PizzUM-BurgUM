package uy.um.edu.pizzumandburgum.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HamburguesaProductoRequestDTO {
    private Long idProducto;
    private int cantidad;
}
