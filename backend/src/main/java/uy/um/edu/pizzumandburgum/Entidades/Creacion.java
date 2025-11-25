package uy.um.edu.pizzumandburgum.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "creacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PedidoCreacion> creacionesPedido = new ArrayList<>();

    @OneToMany(mappedBy = "creacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Favorito> favoritos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
