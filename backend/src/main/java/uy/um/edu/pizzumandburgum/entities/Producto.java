package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idProducto;
    String tipo;
    boolean sinTacc;

    public Producto(){}

    public Producto(long idProducto, String tipo, boolean sinTacc) {
        this.idProducto = idProducto;
        this.tipo = tipo;
        this.sinTacc = sinTacc;
    }

}
