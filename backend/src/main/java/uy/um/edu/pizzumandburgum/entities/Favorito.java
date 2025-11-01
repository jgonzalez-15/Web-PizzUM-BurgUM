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
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne(optional = false)
    @JoinColumn (name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn (name = "creacion_id")
    private Creacion creacion;

    private float precio;
}
