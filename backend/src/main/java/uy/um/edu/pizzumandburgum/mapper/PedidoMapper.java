package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
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
            numeroMedioDePago = pedido.getMedioDePago().getNumero();
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

        return new PedidoResponseDTO(pedido.getPrecio(), pedido.getFecha(), pedido.getEstado(), pedido.getClienteAsignado().getEmail(), pedido.isEstaPago(), creacionesDTO, bebidasDTO, numeroMedioDePago);
    }
}
