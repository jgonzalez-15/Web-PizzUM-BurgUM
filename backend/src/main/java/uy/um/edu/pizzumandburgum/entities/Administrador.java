package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

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
