package uy.um.edu.pizzumandburgum.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomicilioResponseDTO {
    private Long id;
    private String direccion;
    private List<PedidoResponseDTO> pedidos;
    private boolean estaActivo;
}

