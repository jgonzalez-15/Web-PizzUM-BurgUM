package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.ClienteRequestDTO;
import uy.um.edu.pizzumandburgum.dto.request.DomicilioRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.DomicilioResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.entities.Pedido;

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
        return new DomicilioResponseDTO(domicilio.getId(), domicilio.getDireccion(),pedidosResponse);
    }
}
