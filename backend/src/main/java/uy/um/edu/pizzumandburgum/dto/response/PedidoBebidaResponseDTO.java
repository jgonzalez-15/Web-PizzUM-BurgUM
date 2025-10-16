package uy.um.edu.pizzumandburgum.dto.response;

import lombok.Getter;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.entities.Producto;

@Getter
@Setter
public class PedidoBebidaResponseDTO {
    private Pedido pedido;
    private Producto producto;
    private int cantidad;

    public PedidoBebidaResponseDTO() {
    }

    public PedidoBebidaResponseDTO(Pedido pedido, Producto producto, int cantidad) {
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }
}
