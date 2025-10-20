package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hamburguesa extends Creacion {
    int cantCarnes;

    @OneToMany(mappedBy = "hamburguesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HamburguesaProducto> ingredientes = new ArrayList<>();


}
