package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPedido;
    float precio;
    LocalDate fecha;
    String estado;
    boolean estaPago;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente clienteAsignado;

    @ManyToOne
    @JoinColumn(name = "domicilio")
    private Domicilio domicilio;

    @ManyToOne
    @JoinColumn(name = "medioDePago")
    private MedioDePago medioDePago;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoCreacion> creacionesPedido = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoBebida>bebidas = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "pedido")
    private PagoDummy dummy;


}
