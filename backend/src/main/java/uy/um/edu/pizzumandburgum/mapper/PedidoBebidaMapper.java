package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.PedidoBebidaRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;

@Component
public class PedidoBebidaMapper {

    @Autowired
    private ProductoMapper productoMapper;
    public PedidoBebida toEntity(PedidoBebidaRequestDTO dto) {
        PedidoBebida pedidoBebida = new PedidoBebida();
        pedidoBebida.setProducto(productoMapper.toEntity(dto.getProducto()));
        pedidoBebida.setCantidad(dto.getCantidad());

        return pedidoBebida;
    }

    public PedidoBebidaResponseDTO toResponseDTO(PedidoBebida pedidoBebida) {
        return new PedidoBebidaResponseDTO(pedidoBebida.getId(), productoMapper.toResponseDTO(pedidoBebida.getProducto()),pedidoBebida.getCantidad());
    }
}
