package uy.um.edu.pizzumandburgum.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoMDPModificaciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedioDePago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numeroTarjeta;
    private LocalDate fechaVencimiento;
    private String nombreTitular;
    private boolean estaActivo;

    @ManyToOne
    @JoinColumn (name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "medioDePago", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "medioDePago", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoMDPModificaciones> historico = new ArrayList<>();



}
