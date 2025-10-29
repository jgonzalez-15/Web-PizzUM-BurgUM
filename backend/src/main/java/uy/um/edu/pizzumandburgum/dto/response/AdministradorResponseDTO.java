package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorResponseDTO {
    private String email;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private long telefono;
    private LocalDate fechaNac;
}
