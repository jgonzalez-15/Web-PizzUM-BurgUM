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
    private long id_creacion;
    private float precio;

    @OneToMany(mappedBy = "creacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoCreacion> creacionesPedido = new ArrayList<>();

    @OneToMany(mappedBy = "creacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favoritos> creacionesFavoritas = new ArrayList<>();

}
