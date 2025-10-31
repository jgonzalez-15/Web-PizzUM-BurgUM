package uy.um.edu.pizzumandburgum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Creacion;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;
import uy.um.edu.pizzumandburgum.exceptions.Creacion.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.Pedido.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.repository.CreacionRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoCreacionRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.Interfaces.PedidoCrecionService;

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
        Pedido pedido = (Pedido) pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNoEncontradoException());

        Creacion creacion =(Creacion) creacionRepository.findById(creacionId).orElseThrow(() -> new CreacionNoEncontradaException());

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
