package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    Cliente clienteAsignado;

    public Pedido(){}

    public Pedido(long idPedido, float precio, Date fecha, String estado) {
        this.idPedido = idPedido;
        this.precio = precio;
        this.fecha = fecha;
        this.estado = estado;
    }
}
