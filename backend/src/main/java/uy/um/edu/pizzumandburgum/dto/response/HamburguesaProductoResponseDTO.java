package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HamburguesaProductoResponseDTO {
        private Long idHamburguesa;
        private Long idProducto;
        private int cantidad;
}
