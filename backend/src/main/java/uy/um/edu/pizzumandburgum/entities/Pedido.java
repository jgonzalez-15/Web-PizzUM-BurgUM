package uy.um.edu.pizzumandburgum.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
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

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    private List<PedidoCreacion> creacionesPedido = new ArrayList<>();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PedidoBebida>bebidas = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "pedido")
    private PagoDummy dummy;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonIgnore
    private Calificacion calificacion;


}
