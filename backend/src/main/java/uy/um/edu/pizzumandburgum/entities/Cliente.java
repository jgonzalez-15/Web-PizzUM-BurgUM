package uy.um.edu.pizzumandburgum.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "clienteAsignado", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favoritos> creacionesFavoritas = new ArrayList<>();

    //Para JPA


    public Cliente() {
    }

    public Cliente(String email, String nombre, String apellido, String password, long telefono, LocalDate fechaNac, List<Pedido> pedidos, List<Favoritos> creacionesFavoritas) {
        super(email, nombre, apellido, password, telefono, fechaNac);
        this.pedidos = pedidos;
        this.creacionesFavoritas = creacionesFavoritas;
    }
}
