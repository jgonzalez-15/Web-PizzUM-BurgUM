package uy.um.edu.pizzumandburgum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalificacionResponseDTO {
    private Long id;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fecha;
    private Long idPedido;
    private String idCliente;
}
