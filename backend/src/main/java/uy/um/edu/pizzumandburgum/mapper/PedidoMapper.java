package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;

@Component
public class PedidoMapper {
    public Pedido toEntity(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setPrecio(dto.getPrecio());
        pedido.setFecha(dto.getFecha());
        pedido.setEstado(dto.getEstado());
        pedido.setCreacionesPedido(dto.getCreacionesPedido());
        pedido.setBebidas(dto.getBebidas());
        pedido.setClienteAsignado(dto.getClienteAsignado());
        return pedido;
    }

    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
        return new PedidoResponseDTO(pedido.getPrecio(), pedido.getFecha(),pedido.getEstado(),pedido.getClienteAsignado().getEmail(),pedido.getCreacionesPedido(),pedido.getBebidas(), pedido.getMedioDePago().getNumero());
    }
}
