package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Pedido;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedioDePagoDTO {
    Long numero;
    LocalDate vencimiento;
    String direccion;
    ClienteResponseDTO Cliente;
    List<PedidoResponseDTO> pedidos;


}
