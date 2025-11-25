package uy.um.edu.pizzumandburgum.Servicios.Implementación;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.Entidades.Creacion;
import uy.um.edu.pizzumandburgum.Entidades.Pedido;
import uy.um.edu.pizzumandburgum.Entidades.PedidoCreacion;
import uy.um.edu.pizzumandburgum.Excepciones.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.Excepciones.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.Repositorios.CreacionRepository;
import uy.um.edu.pizzumandburgum.Repositorios.PedidoCreacionRepository;
import uy.um.edu.pizzumandburgum.Repositorios.PedidoRepository;
import uy.um.edu.pizzumandburgum.Servicios.Interfaces.PedidoCrecionService;

@Service
public class PedidoCreacionServiceImpl implements PedidoCrecionService {

    @Autowired
    private PedidoCreacionRepository pedidoCreacionRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CreacionRepository creacionRepository;


    @Override
    public PedidoCreacion agregarCreacion(Long pedidoId, Long creacionId, int cantidad) {
        Pedido pedido = (Pedido) pedidoRepository.findById(pedidoId).orElseThrow(PedidoNoEncontradoException::new);

        Creacion creacion = (Creacion) creacionRepository.findById(creacionId).orElseThrow(CreacionNoEncontradaException::new);

        PedidoCreacion pedidoCreacion = new PedidoCreacion();
        pedidoCreacion.setCreacion(creacion);
        pedidoCreacion.setPedido(pedido);
        pedidoCreacion.setCantidad(cantidad);

        PedidoCreacion guardado = pedidoCreacionRepository.save(pedidoCreacion);
        creacion.getCreacionesPedido().add(guardado);

        pedido.getCreacionesPedido().add(guardado);

        return guardado;
    }
}
