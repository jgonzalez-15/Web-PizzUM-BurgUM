package uy.um.edu.pizzumandburgum.dto.response.Historicos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoMDPRepsonseDTO {
    private Long id;

    private Long idMDP;
    private LocalDate fechaModificacion;
    private String tipoModificiacion;

    private Long numeroViejo;
    private LocalDate vencimientoViejo;
    private String nombreTitularViejo;


    private Long numeroNuevo;
    private LocalDate vencimientoNuevo;
    private String nombreTitularNuevo;
}
