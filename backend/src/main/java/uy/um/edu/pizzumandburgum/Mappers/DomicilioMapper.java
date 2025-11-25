package uy.um.edu.pizzumandburgum.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.DTOs.Request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Domicilio;
import uy.um.edu.pizzumandburgum.Entidades.Pedido;

import java.util.ArrayList;
import java.util.List;

@Component
public class DomicilioMapper {

    @Autowired
    private PedidoMapper pedidoMapper;

    public Domicilio toEntity(DomicilioRequestDTO dto) {
        Domicilio domicilio = new Domicilio();
        domicilio.setDireccion(dto.getDireccion());

        return domicilio;
    }

    public DomicilioResponseDTO toResponseDTO(Domicilio domicilio) {
        List<PedidoResponseDTO> pedidosResponse = new ArrayList<>();

        for (Pedido pedido : domicilio.getPedidos()) {
            PedidoResponseDTO pedidoDTO = pedidoMapper.toResponseDTO(pedido);
            pedidosResponse.add(pedidoDTO);
        }
        return new DomicilioResponseDTO(
                domicilio.getId(),
                domicilio.getDireccion(),
                pedidosResponse,
                domicilio.isEstaActivo()
        );
    }
}
