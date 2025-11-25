package uy.um.edu.pizzumandburgum.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRegistrarRequestDTO {
    private String email;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private Long telefono;
    private LocalDate fechaNac;
    private List<DomicilioRequestDTO> domicilios;
    private List<MedioDePagoRequestDTO>  mediosDePago;
    private Long cedula;
}
