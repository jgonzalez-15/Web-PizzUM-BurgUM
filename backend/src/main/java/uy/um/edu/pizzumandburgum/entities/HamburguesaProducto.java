package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HamburguesaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad; // número de unidades de ese producto

    @ManyToOne
    @JoinColumn(name = "hamburguesaId")
    private Hamburguesa hamburguesa;

    @ManyToOne
    @JoinColumn(name = "productoId")
    private Producto producto;

    public HamburguesaProducto() {}

    public HamburguesaProducto(Hamburguesa hamburguesa, Producto producto, int cantidad) {
        this.hamburguesa = hamburguesa;
        this.producto = producto;
        this.cantidad = cantidad;
    }

}
