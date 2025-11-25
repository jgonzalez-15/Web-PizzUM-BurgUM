package uy.um.edu.pizzumandburgum.Entidades.Historicos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.Entidades.Administrador;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoAdministradorModificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

    @Column(nullable = false)
    private LocalDate fechaModificacion;

    @Column(nullable = false)
    private String tipoModificacion;

    private String nombreViejo;
    private String apellidoViejo;
    private String contraseniaVieja;
    private Long telefonoViejo;
    private LocalDate fechaNacVieja;
    private String domicilioViejo;

    private String nombreNuevo;
    private String apellidoNuevo;
    private String contraseniaNueva;
    private Long telefonoNuevo;
    private LocalDate fechaNacNueva;
    private String domicilioNuevo;
}
