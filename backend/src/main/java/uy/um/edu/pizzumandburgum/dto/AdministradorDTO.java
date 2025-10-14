package uy.um.edu.pizzumandburgum.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AdministradorDTO {
    private String email;
    private String nombre;
    private String apellido;
    private String password;
    private long telefono;
    private Date fechaNac;

    public AdministradorDTO() {
    }

    public AdministradorDTO(String email, String nombre, String apellido, String password, long telefono, Date fechaNac) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
    }
}
