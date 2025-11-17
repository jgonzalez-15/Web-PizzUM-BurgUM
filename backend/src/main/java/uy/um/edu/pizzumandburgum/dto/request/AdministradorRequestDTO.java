package uy.um.edu.pizzumandburgum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorRequestDTO {
    private String email;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private Long telefono;
    private LocalDate fechaNac;
    private Long cedula;
    private Long domicilio;
}
