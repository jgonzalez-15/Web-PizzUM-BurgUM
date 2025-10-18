package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;
import uy.um.edu.pizzumandburgum.mapper.PedidoMapper;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.PedidoBebidaService;
import uy.um.edu.pizzumandburgum.service.PedidoCrecionService;
import uy.um.edu.pizzumandburgum.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    @Autowired
    private PedidoBebidaService pedidoBebidaService;

    @Autowired
    private PedidoCrecionService pedidoCrecionService;

    @Override
    public PedidoResponseDTO realizarPedido(PedidoRequestDTO pedidoRequest) {
        Pedido pedido =  pedidoMapper.toEntity(pedidoRequest);
        pedido.setEstado("En cola");
        pedido = pedidoRepository.save(pedido);

        float precio = 0;

        for (PedidoCreacion pc : pedidoRequest.getCreacionesPedido()) {
            pedidoCrecionService.agregarCreacion(pedido.getIdPedido(), pc.getCreacion().getId_creacion(), pc.getCantidad());
            precio += pc.getCreacion().getPrecio() * pc.getCantidad();
            }


        for (PedidoBebida pb : pedidoRequest.getBebidas()) {
            pedidoBebidaService.agregarBebida(pedido.getIdPedido(), pb.getProducto().getIdProducto(), pb.getCantidad());
            precio += pb.getCantidad()*pb.getProducto().getPrecio();
        }

        pedido.setPrecio(precio);

        return pedidoMapper.toResponseDTO(pedido);
    }
}
