package uy.um.edu.pizzumandburgum.dto.response.Historicos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

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
