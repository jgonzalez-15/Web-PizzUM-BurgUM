package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;

@Component
public class PedidoMapper {
    public Pedido toEntity(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setEstado(dto.getEstado());
        pedido.setEstaPago(dto.isEstaPago());
        return pedido;
    }

    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
        return new PedidoResponseDTO(pedido.getPrecio(), pedido.getFecha(),pedido.getEstado(),pedido.getClienteAsignado().getEmail(), pedido.isEstaPago(), pedido.getCreacionesPedido(),pedido.getBebidas(), pedido.getMedioDePago().getNumero());
    }
}
