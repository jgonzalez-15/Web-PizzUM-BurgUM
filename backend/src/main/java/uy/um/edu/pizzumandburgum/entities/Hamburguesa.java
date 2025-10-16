package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Hamburguesa extends Creacion {
    int cantCarnes;

    @OneToMany(mappedBy = "hamburguesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HamburguesaProducto> ingredientes = new ArrayList<>();

    public Hamburguesa(){}

    public Hamburguesa(int cantCarnes, List<HamburguesaProducto> ingredientes) {
        this.cantCarnes = cantCarnes;
        this.ingredientes = ingredientes;
    }

}
