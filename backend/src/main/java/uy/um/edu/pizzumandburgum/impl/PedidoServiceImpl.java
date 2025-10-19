package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoCreacionDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.exceptions.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.mapper.PedidoMapper;
import uy.um.edu.pizzumandburgum.repository.ClienteDomicilioRepository;
import uy.um.edu.pizzumandburgum.repository.ClienteRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.*;

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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteDomicilioService clienteDomicilioService;

    @Autowired
    private MedioDePagoService medioDePagoService;

    @Override
    public PedidoResponseDTO realizarPedido(String email,String direccion,PedidoRequestDTO pedidoRequest, Long numero) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(() -> new ClienteNoExisteException());

        Domicilio domicilio = clienteDomicilioService.obtenerDomicilio(email,direccion);

        Pedido pedido =  pedidoMapper.toEntity(pedidoRequest);
        pedido.setEstado("En cola");
        pedido.setDomicilio(domicilio);
        pedido.setClienteAsignado(cliente);
        pedido.setMedioDePago(medioDePagoService.obtenerMedioDePago(cliente.getEmail(),numero));
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

    @Override
    public void eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new PedidoNoEncontradoException();
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    public String consultarEstado(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNoEncontradoException());
        return pedido.getEstado();
    }

}
