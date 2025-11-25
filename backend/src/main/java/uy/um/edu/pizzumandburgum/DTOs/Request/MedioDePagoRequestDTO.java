package uy.um.edu.pizzumandburgum.DTOs.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedioDePagoRequestDTO {
    Long numeroTarjeta;
    LocalDate fechaVencimiento;
    String nombreTitular;

}
