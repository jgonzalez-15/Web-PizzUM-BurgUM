package uy.um.edu.pizzumandburgum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.dto.response.ProductoResponseDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoBebidaRequestDTO {
    private ProductoRequestDTO producto;
    private int cantidad;
}
