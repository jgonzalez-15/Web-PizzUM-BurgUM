package uy.um.edu.pizzumandburgum.DTOs.Response;

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
public class MedioDePagoDTO {
    Long id;
    Long numeroTarjeta;
    LocalDate fechaVencimiento;
    String nombreTitular;
    ClienteResponseDTO Cliente;
    List<PedidoResponseDTO> pedidos;
    boolean estaActivo;

}
