package uy.um.edu.pizzumandburgum.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.Entidades.Historicos.HistoricoProductoModificacion;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProducto;
    String nombre;
    String tipo;
    boolean sinTacc;
    Float precio;
    boolean estaActivo = true;
    boolean visible = true;


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HamburguesaProducto> ingredientesHamburguesa = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PizzaProducto> ingredientesPizza = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoBebida> bebidas = new ArrayList<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoProductoModificacion> historico = new ArrayList<>();


}
