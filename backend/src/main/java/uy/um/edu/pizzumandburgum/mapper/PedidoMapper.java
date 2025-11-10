package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.*;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;

import java.util.ArrayList;
import java.util.List;

@Component
public class PedidoMapper {

    @Autowired
    private CreacionMapper creacionMapper;

    @Autowired
    private ProductoMapper productoMapper;


    public Pedido toEntity(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        return pedido;
    }

    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
        Long numeroMedioDePago = null;
        if (pedido.getMedioDePago() != null) {
            numeroMedioDePago = pedido.getMedioDePago().getNumeroTarjeta();
        }

        List<PedidoCreacionDTO> creacionesDTO = new ArrayList<>();
        if (pedido.getCreacionesPedido() != null) {
            for (PedidoCreacion pc : pedido.getCreacionesPedido()) {
                PedidoCreacionDTO dto = new PedidoCreacionDTO();
                dto.setCantidad(pc.getCantidad());
                dto.setCreacion(creacionMapper.toResponseDTO(pc.getCreacion()));
                creacionesDTO.add(dto);
            }
        }

        List<PedidoBebidaResponseDTO> bebidasDTO = new ArrayList<>();
        if (pedido.getBebidas() != null) {
            for (PedidoBebida pb : pedido.getBebidas()) {
                PedidoBebidaResponseDTO dto = new PedidoBebidaResponseDTO();
                dto.setCantidad(pb.getCantidad());
                dto.setProducto(productoMapper.toResponseDTO(pb.getProducto()));
                bebidasDTO.add(dto);
            }
        }

        DomicilioSinPedidosResponseDTO domicilioDTO = null;
        if (pedido.getDomicilio() != null) {
            domicilioDTO = new DomicilioSinPedidosResponseDTO(pedido.getDomicilio().getId(), pedido.getDomicilio().getDireccion(), pedido.getDomicilio().isEstaActivo());
        }


        return new PedidoResponseDTO(pedido.getId(), pedido.getPrecio(), pedido.getFecha(), pedido.getEstado(), pedido.getClienteAsignado().getEmail(), pedido.isEstaPago(), domicilioDTO, creacionesDTO, bebidasDTO, numeroMedioDePago);
    }
}
