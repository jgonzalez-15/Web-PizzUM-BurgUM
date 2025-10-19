package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PizzaProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad; // número de unidades de ese producto

    @ManyToOne
    @JoinColumn(name = "pizzaId")
    private Pizza pizza;

    @ManyToOne
    @JoinColumn(name = "productoId")
    private Producto producto;
}
