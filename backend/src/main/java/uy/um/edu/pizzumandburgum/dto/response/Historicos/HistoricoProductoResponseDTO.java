package uy.um.edu.pizzumandburgum.dto.response.Historicos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Producto;

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
    private float precioAnterior;

    private String nombreNuevo;
    private String tipoNuevo;
    private boolean sinTaccNuevo;
    private float precioNuevo;
}
