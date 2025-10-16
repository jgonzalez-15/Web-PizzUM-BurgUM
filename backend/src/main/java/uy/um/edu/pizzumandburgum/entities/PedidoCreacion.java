package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PedidoCreacion {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedidoId")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "creacionId")
    private Creacion creacion;

    private int cantidad;

    public PedidoCreacion() {
    }

    public PedidoCreacion(Long id, Pedido pedido, Creacion creacion, int cantidad) {
        this.id = id;
        this.pedido = pedido;
        this.creacion = creacion;
        this.cantidad = cantidad;
    }
}
