package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Administrador extends Usuario{

    public Administrador() {
    }

    public Administrador(String email, String nombre, String apellido, String contrasenia, long telefono, LocalDate fechaNac) {
        super(email, nombre, apellido, contrasenia, telefono, fechaNac);
    }
}
