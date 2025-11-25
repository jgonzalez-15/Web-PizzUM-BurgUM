package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.Entidades.Pedido;
import uy.um.edu.pizzumandburgum.Entidades.PedidoBebida;
import uy.um.edu.pizzumandburgum.Entidades.Producto;
import uy.um.edu.pizzumandburgum.Excepciones.Bebida.BebidaNoEncontradaException;
import uy.um.edu.pizzumandburgum.Excepciones.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.Repositorios.PedidoBebidaRepository;
import uy.um.edu.pizzumandburgum.Repositorios.PedidoRepository;
import uy.um.edu.pizzumandburgum.Repositorios.ProductoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.PedidoBebidaService;

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
