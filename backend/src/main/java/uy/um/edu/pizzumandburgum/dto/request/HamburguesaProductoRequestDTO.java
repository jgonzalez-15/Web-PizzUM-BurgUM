package uy.um.edu.pizzumandburgum.dto.request;

import lombok.Getter;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;
import uy.um.edu.pizzumandburgum.entities.Producto;

@Getter
@Setter
public class HamburguesaProductoRequestDTO {
    private Hamburguesa hamburguesa;
    private Producto producto;
    private int cantidad;

    public HamburguesaProductoRequestDTO() {
    }

    public HamburguesaProductoRequestDTO(Hamburguesa hamburguesa, Producto producto, int cantidad) {
        this.hamburguesa = hamburguesa;
        this.producto = producto;
        this.cantidad = cantidad;
    }
}
