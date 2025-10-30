package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Creacion;
import uy.um.edu.pizzumandburgum.entities.Pedido;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoCreacionDTO {
    private CreacionResponseDTO creacion;
    private PedidoResponseDTO pedido;
    private int cantidad;
}
