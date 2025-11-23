package uy.um.edu.pizzumandburgum.dto.response.Historicos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
