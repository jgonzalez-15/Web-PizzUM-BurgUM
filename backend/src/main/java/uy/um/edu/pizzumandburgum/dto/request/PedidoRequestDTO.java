package uy.um.edu.pizzumandburgum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
        private String idCliente;
        private Long idDomicilio;
        private Long idMedioDePago;
        private List<PedidoCreacionRequestDTO> creaciones;
        private List<PedidoBebidaRequestDTO> bebidas;
}