package uy.um.edu.pizzumandburgum.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    //Para JPA
    public Cliente() {}

    public Cliente(String email, String nombre, String apellido, String password, long telefono, Date fechaNac, List<Pedido> pedidos) {
        super(email, nombre, apellido, password, telefono, fechaNac);
        this.pedidos = pedidos;
    }
}
