package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedioDePago {
    @Id
    Long numero;
    Date vencimiento;
    String direccion;
    @ManyToOne(optional = false)
    @JoinColumn (name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "medioDePago", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();



}
