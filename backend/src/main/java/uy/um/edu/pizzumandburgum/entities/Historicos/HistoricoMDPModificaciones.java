package uy.um.edu.pizzumandburgum.entities.Historicos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoMDPModificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "medio_de_pago_id", nullable = false)
    private MedioDePago medioDePago;

    @Column(nullable = false)
    private LocalDate fechaModificacion;

    @Column(nullable = false)
    private String tipoModificiacion;

    Long numeroViejo;
    LocalDate vencimientoViejo;
    String direccionViejo;

    Long numeroNuevo;
    LocalDate vencimientoNuevo;
    String direccionNueva;

}
