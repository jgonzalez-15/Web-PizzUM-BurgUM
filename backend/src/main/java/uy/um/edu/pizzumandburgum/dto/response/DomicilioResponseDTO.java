package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomicilioResponseDTO {
    private long id;
    private String calle;
    private String numero;
    private String ciudad;
    private String departamento;
    private String codigoPostal;
}
