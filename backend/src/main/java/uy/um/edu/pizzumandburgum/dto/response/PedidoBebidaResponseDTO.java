package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.dto.request.ProductoDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.entities.Producto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoBebidaResponseDTO {
    private Long id;
    private ProductoDTO Producto;
    private int cantidad;

}
