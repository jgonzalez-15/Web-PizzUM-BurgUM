package uy.um.edu.pizzumandburgum.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Usuario {


    @OneToMany(mappedBy = "clienteAsignado", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favoritos> creacionesFavoritas = new ArrayList<>();


}
