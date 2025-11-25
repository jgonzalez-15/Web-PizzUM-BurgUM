package uy.um.edu.pizzumandburgum.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
