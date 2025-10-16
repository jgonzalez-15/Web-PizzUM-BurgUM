package uy.um.edu.pizzumandburgum.dto.response;

import lombok.Getter;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {
    float precio;
    LocalDate fecha;
    String estado;
    Cliente clienteAsignado;
    private List<PedidoCreacion> creacionesPedido;
    private List<PedidoBebida> bebidas;

    public PedidoResponseDTO(float precio, LocalDate fecha, String estado, Cliente clienteAsignado, List<PedidoCreacion> creacionesPedido, List<PedidoBebida> bebidas) {
        this.precio = precio;
        this.fecha = fecha;
        this.estado = estado;
        this.clienteAsignado = clienteAsignado;
        this.creacionesPedido = creacionesPedido;
        this.bebidas = bebidas;
    }
}
