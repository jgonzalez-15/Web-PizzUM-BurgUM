package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Creacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float precio;
    private boolean esFavorita;

    @OneToMany(mappedBy = "creacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoCreacion> creacionesPedido = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cliente_id") // nombre de la columna FK en la BD
    private Cliente cliente;

}
