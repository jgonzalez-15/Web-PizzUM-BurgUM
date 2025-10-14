package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Hamburguesa extends Creacion {
    int cantCarnes;

    public Hamburguesa(){}

    public Hamburguesa(int cantCarnes){
        this.cantCarnes = cantCarnes;
    }

}
