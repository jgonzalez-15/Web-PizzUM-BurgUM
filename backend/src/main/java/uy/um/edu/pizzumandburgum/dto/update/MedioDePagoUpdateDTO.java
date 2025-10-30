package uy.um.edu.pizzumandburgum.dto.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedioDePagoUpdateDTO {
    Long numero;
    LocalDate vencimiento;
    String direccion;
}
