package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;
import uy.um.edu.pizzumandburgum.entities.Producto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    float precio;
    LocalDate fecha;
    String estado;
    String idClienteAsignado;

    private List<PedidoCreacion> creacionesPedido;
    private List<PedidoBebida> bebidas;
    private Long idMedioDePago;
}
