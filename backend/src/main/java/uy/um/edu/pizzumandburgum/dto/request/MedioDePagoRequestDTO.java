package uy.um.edu.pizzumandburgum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedioDePagoRequestDTO {
    Long numero;
    LocalDate vencimiento;
    String direccion;
    ClienteResponseDTO Cliente;
}
