package uy.um.edu.pizzumandburgum.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoProductoModificaciones {
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
    private float precioAnterior;

    private String nombreNuevo;
    private String tipoNuevo;
    private boolean sinTaccNuevo;
    private float precioNuevo;
}
