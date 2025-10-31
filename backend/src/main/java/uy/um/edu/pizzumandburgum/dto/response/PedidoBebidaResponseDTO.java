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
public class PedidoBebidaResponseDTO {
    private Long id;
    private ProductoResponseDTO Producto;
    private int cantidad;

}
