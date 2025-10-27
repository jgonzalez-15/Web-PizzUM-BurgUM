package uy.um.edu.pizzumandburgum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.dto.response.HamburguesaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.Producto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HamburguesaProductoRequestDTO {
    private Long idHamburguesa;
    private Long idProducto;
    private int cantidad;
}
