package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.*;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.exceptions.Usuario.Cliente.ClienteNoExisteException;
import uy.um.edu.pizzumandburgum.mapper.PedidoMapper;
import uy.um.edu.pizzumandburgum.repository.*;
import uy.um.edu.pizzumandburgum.service.Interfaces.*;

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
    private PedidoCreacionRepository pedidoCreacionRepository;

    @Autowired
    private MedioDePagoService medioDePagoService;

    @Autowired
    private PedidoBebidaRepository pedidoBebidaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public PedidoResponseDTO realizarPedido(String email, long idDomicilio, Long idPedido, Long numero) {
        Cliente cliente = clienteRepository.findById(email).orElseThrow(() -> new ClienteNoExisteException());
        MedioDePago medioDePago = medioDePagoService.obtenerMedioDePago(cliente.getEmail(), numero);
        Domicilio domicilio = clienteDomicilioService.obtenerDomicilio(email, idDomicilio);

        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new PedidoNoEncontradoException());
        pedido.setEstado("En cola");
        pedido.setDomicilio(domicilio);
        pedido.setClienteAsignado(cliente);
        pedido.setMedioDePago(medioDePago);
        pedido = pedidoRepository.save(pedido);

        float precio = 0;

        for (PedidoCreacion pc : pedido.getCreacionesPedido()) {
            pedidoCrecionService.agregarCreacion(
                    pedido.getIdPedido(),
                    pc.getCreacion().getIdCreacion(),
                    pc.getCantidad()
            );
            precio += pc.getCreacion().getPrecio() * pc.getCantidad();
        }

        for (PedidoBebida pb : pedido.getBebidas()) {
            pedidoBebidaService.agregarBebida(
                    pedido.getIdPedido(),
                    pb.getProducto().getIdProducto(),
                    pb.getCantidad()
            );

            precio += pb.getProducto().getPrecio() * pb.getCantidad();

            pedido.setPrecio(precio);

            return pedidoMapper.toResponseDTO(pedido);
        }

    return null;}

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

    @Override
    public void cambiarEstado(Long id, String estado) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new PedidoNoEncontradoException());
        pedido.setEstado(estado);
    }
}
