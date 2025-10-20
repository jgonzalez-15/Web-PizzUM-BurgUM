package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;

@Component
public class PedidoBebidaMapper {
    public PedidoBebida toEntity(PedidoBebidaResponseDTO dto) {
        PedidoBebida pedidoBebida = new PedidoBebida();
        pedidoBebida.setPedido(dto.getPedido());
        pedidoBebida.setPedido(dto.getPedido());
        pedidoBebida.setCantidad(dto.getCantidad());

        return pedidoBebida;
    }

    public PedidoBebidaResponseDTO toResponseDTO(PedidoBebida pedidoBebida) {
        return new PedidoBebidaResponseDTO(pedidoBebida.getPedido(),pedidoBebida.getProducto(),pedidoBebida.getCantidad());
    }
}
