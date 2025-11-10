package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoDummyResponseDTO {
    private String codigoTransaccion;
    private float monto;
    private LocalDate fechaPago;
    private String estadoPago;
    private Long idPedido;
}
