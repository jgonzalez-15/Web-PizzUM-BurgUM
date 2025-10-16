package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idProducto;
    String tipo;
    String subtipo;
    boolean sinTacc;

    @OneToMany(mappedBy = "Productos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HamburguesaProducto> ingredientesHamburguesa = new ArrayList<>();

    public Producto(){}

    public Producto(long idProducto, String tipo, boolean sinTacc) {
        this.idProducto = idProducto;
        this.tipo = tipo;
        this.sinTacc = sinTacc;
    }

}
