package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class MedioDePagoMapper {

    @Autowired
    private  ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private PedidoMapper pedidoMapper;

    public MedioDePago toEntity(MedioDePagoRequestDTO dto) {
        MedioDePago medioDePago = new MedioDePago();
        medioDePago.setNombreTitular(dto.getNombreTitular());
        medioDePago.setNumeroTarjeta(dto.getNumeroTarjeta());
        medioDePago.setFechaVencimiento(dto.getFechaVencimiento());
        return medioDePago;
    }

    public MedioDePagoDTO toResponseDTO(MedioDePago medioDePago) {
        Cliente cliente = clienteRepository.findById(medioDePago.getCliente().getEmail()).orElseThrow(ClienteNoExisteException::new);
        ClienteResponseDTO clienteResponseDTO = clienteMapper.toResponseDTO(cliente);
        List<PedidoResponseDTO>pedidos = new ArrayList<>();
        for (Pedido pedido: medioDePago.getPedidos()){
            PedidoResponseDTO pedidoResponseDTO = pedidoMapper.toResponseDTO(pedido);
            pedidos.add(pedidoResponseDTO);
        }
        return new MedioDePagoDTO(medioDePago.getId(), medioDePago.getNumeroTarjeta(), medioDePago.getFechaVencimiento(), medioDePago.getNombreTitular(), clienteResponseDTO,pedidos);
    }

    public MedioDePago toEntityDTO(MedioDePagoDTO dto) {
        MedioDePago medioDePago = new MedioDePago();
        medioDePago.setNombreTitular(dto.getNombreTitular());
        medioDePago.setNumeroTarjeta(dto.getNumeroTarjeta());
        medioDePago.setFechaVencimiento(dto.getFechaVencimiento());
        return medioDePago;
    }
}
