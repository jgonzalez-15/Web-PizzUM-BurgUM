package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    boolean estaPago;

    private List<PedidoCreacionDTO> creacionesPedido;
    private List<PedidoBebidaResponseDTO> bebidas;
    private Long idMedioDePago;
}
