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
public class HistoricoDomicilioResponseDTO {
    private Long id;

    private Long idDomicilio;

    private LocalDate fechaModificacion;

    private String tipoModificiacion;

    private String direccionNueva;
    private String direccionVieja;
}
