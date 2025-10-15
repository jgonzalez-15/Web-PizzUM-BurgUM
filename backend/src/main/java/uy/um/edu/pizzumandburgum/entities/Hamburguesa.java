package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Hamburguesa extends Creacion {
    int cantCarnes;

    @ManyToMany
    @JoinTable(
            name = "hamburguesa_producto",
            joinColumns = @JoinColumn(name = "hamburguesa_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos = new ArrayList<>();

    public Hamburguesa(){}

    public Hamburguesa(int cantCarnes, List<Producto> productos) {
        this.cantCarnes = cantCarnes;
        this.productos = productos;
    }

}
