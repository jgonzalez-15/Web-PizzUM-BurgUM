package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    Long id;
    float precio;
    LocalDate fecha;
    String estado;
    String idClienteAsignado;
    boolean estaPago;
    DomicilioSinPedidosResponseDTO domicilio;

    private List<PedidoCreacionDTO> creacionesPedido;
    private List<PedidoBebidaResponseDTO> bebidas;
    private Long idMedioDePago;
}
