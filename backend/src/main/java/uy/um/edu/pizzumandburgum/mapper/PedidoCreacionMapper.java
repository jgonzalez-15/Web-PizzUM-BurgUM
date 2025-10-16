package uy.um.edu.pizzumandburgum.mapper;

import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;

public class PedidoCreacionMapper {
    public PedidoCreacion toEntity(PedidoCreacionDTO dto) {
        PedidoCreacion pedidoCreacion = new PedidoCreacion();
        pedidoCreacion.setCreacion(dto.getCreacion());
        pedidoCreacion.setPedido(dto.getPedido());
        pedidoCreacion.setCantidad(dto.getCantidad());

        return pedidoCreacion;
    }

    public PedidoCreacionDTO toResponseDTO(PedidoCreacion pedidoCreacion) {
        return new PedidoCreacionDTO(pedidoCreacion.getCreacion(),pedidoCreacion.getPedido(),pedidoCreacion.getCantidad());
    }
}
