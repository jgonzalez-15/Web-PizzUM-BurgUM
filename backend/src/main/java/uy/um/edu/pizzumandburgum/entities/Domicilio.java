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
    private Long id;
    private String direccion;

    @OneToMany(mappedBy = "domicilio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClienteDomicilio> clientes = new ArrayList<>();

    @OneToMany(mappedBy = "domicilio", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;
}


