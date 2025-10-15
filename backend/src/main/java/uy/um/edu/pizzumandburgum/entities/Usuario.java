package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Cada subclase tendrá su propia tabla
@Getter
@Setter
public abstract class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private long telefono;

    @Column(nullable = false)
    private Date fechaNac;

    public Usuario(){}

    public Usuario(String email, String nombre, String apellido, String password, long telefono, Date fechaNac) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
    }
}
