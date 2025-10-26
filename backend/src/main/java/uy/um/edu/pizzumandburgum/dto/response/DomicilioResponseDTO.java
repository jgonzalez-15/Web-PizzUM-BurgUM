package uy.um.edu.pizzumandburgum.dto.response;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.entities.Pedido;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomicilioResponseDTO {
    private Long id;
    private String direccion;
    private List<PedidoResponseDTO> pedidos;
}
