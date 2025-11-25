package uy.um.edu.pizzumandburgum.Entidades.Historicos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.Entidades.Producto;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoProductoModificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private LocalDateTime fechaModificacion;

    @Column(nullable = false)
    private String tipoModificiacion;

    private String nombreAnterior;
    private String tipoAnterior;
    private boolean sinTaccAnterior;
    private Float precioAnterior;

    private String nombreNuevo;
    private String tipoNuevo;
    private boolean sinTaccNuevo;
    private Float precioNuevo;
}
