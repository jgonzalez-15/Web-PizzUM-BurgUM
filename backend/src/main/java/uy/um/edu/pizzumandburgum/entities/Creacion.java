package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Creacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id_creacion;

    @OneToMany(mappedBy = "creacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoCreacion> creacionesPedido = new ArrayList<>();

    @OneToMany(mappedBy = "creacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favoritos> creacionesFavoritas = new ArrayList<>();

    public Creacion(){}

    public Creacion(long id_creacion, List<PedidoCreacion> creacionesPedido, List<Favoritos> creacionesFavoritas) {
        this.id_creacion = id_creacion;
        this.creacionesPedido = creacionesPedido;
        this.creacionesFavoritas = creacionesFavoritas;
    }
}
