package uy.um.edu.pizzumandburgum.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.Entidades.Historicos.HistoricoClienteModificaciones;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Usuario {
    @OneToMany(mappedBy = "clienteAsignado", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Creacion> creaciones = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ClienteDomicilio> domicilios = new ArrayList<>();

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private  List<MedioDePago> mediosDePago = new ArrayList<>();

    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private  List<Favorito> favoritos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoClienteModificaciones> historico = new ArrayList<>();
}
