package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idPedido;
    float precio;
    LocalDate fecha;
    String estado;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    Cliente clienteAsignado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoCreacion> creacionesPedido = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoBebida> bebidas = new ArrayList<>();

    public Pedido(){}

    public Pedido(long idPedido, float precio, LocalDate fecha, String estado, Cliente clienteAsignado, List<PedidoCreacion> creacionesPedido, List<PedidoBebida> bebidas) {
        this.idPedido = idPedido;
        this.precio = precio;
        this.fecha = fecha;
        this.estado = estado;
        this.clienteAsignado = clienteAsignado;
        this.creacionesPedido = creacionesPedido;
        this.bebidas = bebidas;
    }
}
