package uy.um.edu.pizzumandburgum.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomicilioSinPedidosResponseDTO {
    private Long id;
    private String direccion;
    private boolean estaActivo;
}

