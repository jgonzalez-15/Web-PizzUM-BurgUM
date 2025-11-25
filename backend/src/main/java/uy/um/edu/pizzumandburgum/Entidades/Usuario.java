package uy.um.edu.pizzumandburgum.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Usuario {

    @Id
    @Column(nullable = false, unique = true)
    private String email;

    private String nombre;

    private String apellido;

    @Column(nullable = false)
    private String contrasenia;

    private Long telefono;

    private LocalDate fechaNac;

    private Long cedula;

}
