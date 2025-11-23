package uy.um.edu.pizzumandburgum.dto.response.Historicos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoProductoResponseDTO {
    private Long id;

    private Long idProducto;

    private LocalDateTime fechaModificacion;

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
