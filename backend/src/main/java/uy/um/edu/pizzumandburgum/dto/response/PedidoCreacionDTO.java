package uy.um.edu.pizzumandburgum.dto.response;

import lombok.Getter;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Creacion;
import uy.um.edu.pizzumandburgum.entities.Pedido;

@Getter
@Setter
public class PedidoCreacionDTO {
    private Creacion creacion;
    private Pedido pedido;
    private int cantidad;

    public PedidoCreacionDTO() {
    }

    public PedidoCreacionDTO(Creacion creacion, Pedido pedido,  int cantidad) {
        this.creacion = creacion;
        this.pedido = pedido;
        this.cantidad = cantidad;
    }

}
