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
public class Favoritos {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "creacionId")
    private Creacion creacion;

    public Favoritos() {
    }

    public Favoritos(Long id, Cliente cliente, Creacion creacion) {
        this.id = id;
        this.cliente = cliente;
        this.creacion = creacion;
    }
}
