package uy.um.edu.pizzumandburgum.mapper;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.repository.CreacionRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;

@Component
public class PedidoCreacionMapper {
    @Autowired
    private CreacionRepository creacionRepository;

    @Autowired
    @Lazy
    private PedidoMapper pedidoMapper;

    @Autowired
    private CreacionMapper creacionMapper;

    @Autowired
    private PedidoRepository pedidoRepository;
    public PedidoCreacion toEntity(PedidoCreacionDTO dto) {
        PedidoCreacion pedidoCreacion = new PedidoCreacion();
        pedidoCreacion.setCreacion(creacionRepository.findById(dto.getCreacion().getId()).orElseThrow(CreacionNoEncontradaException::new));
        pedidoCreacion.setCantidad(dto.getCantidad());

        return pedidoCreacion;
    }

    public PedidoCreacionDTO toResponseDTO(PedidoCreacion pedidoCreacion) {
        return new PedidoCreacionDTO(creacionMapper.toResponseDTO(pedidoCreacion.getCreacion()),pedidoMapper.toResponseDTO(pedidoCreacion.getPedido()),pedidoCreacion.getCantidad());
    }
}
