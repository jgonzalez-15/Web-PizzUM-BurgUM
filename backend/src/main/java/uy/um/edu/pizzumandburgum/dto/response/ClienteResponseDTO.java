package uy.um.edu.pizzumandburgum.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class ClienteResponseDTO {
    private String email;
    private String nombre;
    private String apellido;
    private long telefono;
    private LocalDate fechaNac;

    public ClienteResponseDTO(String email, String nombre, String apellido, long telefono) {
    }

    public ClienteResponseDTO(String email, String nombre, String apellido, long telefono, LocalDate fechaNac) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
    }
}
