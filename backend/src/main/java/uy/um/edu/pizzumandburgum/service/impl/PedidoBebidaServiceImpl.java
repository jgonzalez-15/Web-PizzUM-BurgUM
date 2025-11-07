package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;
import uy.um.edu.pizzumandburgum.entities.Producto;
import uy.um.edu.pizzumandburgum.exceptions.Bebida.BebidaNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.repository.PedidoBebidaRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.repository.ProductoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.PedidoBebidaService;

@Service
public class PedidoBebidaServiceImpl implements PedidoBebidaService {

    @Autowired
    private PedidoBebidaRepository pedidoBebidaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public PedidoBebida agregarBebida(Long pedidoId, Long bebidaId, int cantidad) {
        Pedido pedido = (Pedido) pedidoRepository.findById(pedidoId).orElseThrow(PedidoNoEncontradoException::new);

        Producto bebida = productoRepository.findByIdProducto(bebidaId).orElseThrow(BebidaNoEncontradaException::new);

        if (!"Bebida".equals(bebida.getTipo())){
            throw new BebidaNoEncontradaException();
        }

        PedidoBebida pedidoBebida = new PedidoBebida();
        pedidoBebida.setPedido(pedido);
        pedidoBebida.setProducto(bebida);
        pedidoBebida.setCantidad(cantidad);

        PedidoBebida guardado = pedidoBebidaRepository.save(pedidoBebida);

        pedido.getBebidas().add(guardado);

        return guardado;
    }
}
