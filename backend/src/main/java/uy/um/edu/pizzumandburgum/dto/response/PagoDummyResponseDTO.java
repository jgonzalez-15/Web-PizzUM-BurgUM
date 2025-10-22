package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoDummyResponseDTO {
    private String codigoTransaccion;
    private float monto;
    private LocalDateTime fechaPago;
    private String estadoPago;
    private Long idPedido;
}
