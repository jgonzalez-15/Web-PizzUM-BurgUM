package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idPedido;
    float precio;
    Date fecha;
    String estado;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    Cliente clienteAsignado;

    public Pedido(){}

    public Pedido(long idPedido, float precio, Date fecha, String estado) {
        this.idPedido = idPedido;
        this.precio = precio;
        this.fecha = fecha;
        this.estado = estado;
    }
}
