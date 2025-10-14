package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pizza extends Creacion {
    long tamanio;

    public Pizza(){}

    public Pizza(long tamanio){
        ;
       this.tamanio = tamanio;
    }
}

