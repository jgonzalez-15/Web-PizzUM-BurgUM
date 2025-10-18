package uy.um.edu.pizzumandburgum.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import uy.um.edu.pizzumandburgum.entities.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PedidoRequestDTO {
    float precio;
    LocalDate fecha;
    String estado;
    Cliente clienteAsignado;
    private List<PedidoCreacion> creacionesPedido;
    private List<PedidoBebida> bebidas;
    private MedioDePago medioDePago;
    private Domicilio domicilio;

    public PedidoRequestDTO(float precio, LocalDate fecha, String estado, Cliente clienteAsignado, List<PedidoCreacion> creacionesPedido, List<PedidoBebida> bebidas) {
        this.precio = precio;
        this.fecha = fecha;
        this.estado = estado;
        this.clienteAsignado = clienteAsignado;
        this.creacionesPedido = creacionesPedido;
        this.bebidas = bebidas;
    }
}
