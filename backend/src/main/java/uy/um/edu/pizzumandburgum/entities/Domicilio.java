package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String calle;
    private String numero;
    private String ciudad;
    private String departamento;
    private String codigoPostal;

    @OneToMany(mappedBy = "domicilio", cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<>();
}


