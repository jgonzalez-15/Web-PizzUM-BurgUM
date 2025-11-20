package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoAdministradorModificaciones;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoClienteModificaciones;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Administrador extends Usuario{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_domicilio")
    private Domicilio domicilio;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean estaActivo = true;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoAdministradorModificaciones> historico = new ArrayList<>();
}
