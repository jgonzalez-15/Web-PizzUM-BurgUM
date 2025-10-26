package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;
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

    public MedioDePago toEntity(MedioDePagoDTO dto) {
        MedioDePago medioDePago = new MedioDePago();
        medioDePago.setDireccion(dto.getDireccion());
        medioDePago.setNumero(dto.getNumero());
        medioDePago.setVencimiento(dto.getVencimiento());
        Cliente cliente = clienteRepository.findById(dto.getCliente().getEmail()).orElseThrow(()-> new ClienteNoExisteException());
        medioDePago.setCliente(cliente);
        return medioDePago;
    }

    public MedioDePagoDTO toResponseDTO(MedioDePago medioDePago) {
        Cliente cliente = clienteRepository.findById(medioDePago.getCliente().getEmail()).orElseThrow(()-> new ClienteNoExisteException());
        ClienteResponseDTO clienteResponseDTO = clienteMapper.toResponseDTO(cliente);
        List<PedidoResponseDTO>pedidos = new ArrayList<>();
        for (Pedido pedido: medioDePago.getPedidos()){
            PedidoResponseDTO pedidoResponseDTO = pedidoMapper.toResponseDTO(pedido);
            pedidos.add(pedidoResponseDTO);
        }
        return new MedioDePagoDTO(medioDePago.getNumero(), medioDePago.getVencimiento(), medioDePago.getDireccion(), clienteResponseDTO,pedidos);
    }
}
