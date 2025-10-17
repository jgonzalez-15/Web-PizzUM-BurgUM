package uy.um.edu.pizzumandburgum.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.um.edu.pizzumandburgum.entities.Creacion;
import uy.um.edu.pizzumandburgum.entities.Pedido;
import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;
import uy.um.edu.pizzumandburgum.exceptions.CreacionNoEncontradaException;
import uy.um.edu.pizzumandburgum.exceptions.PedidoNoEncontradoException;
import uy.um.edu.pizzumandburgum.mapper.PedidoCreacionMapper;
import uy.um.edu.pizzumandburgum.repository.PedidoCreacionRepository;
import uy.um.edu.pizzumandburgum.repository.PedidoRepository;
import uy.um.edu.pizzumandburgum.service.PedidoCrecionService;

@Service
public class PedidoCreacionServiceImpl implements PedidoCrecionService {
    @Autowired
    private PedidoCreacionRepository pedidoCreacionRepository;

    @Autowired
    private PedidoCreacionMapper pedidoCreacionMappe;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CreacionRepository creacionRepository;


    @Override
    public PedidoCreacion agregarCreacion(Long pedidoId, Long creacionId, int cantidad) {
        Pedido pedido = (Pedido) pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNoEncontradoException());

        Creacion creacion = creacionRepository.findById(creacionId).orElseThrow(() -> new CreacionNoEncontradaException());

        PedidoCreacion pedidoCreacion = new PedidoCreacion();
        pedidoCreacion.setCreacion(creacion);
        pedidoCreacion.setPedido(pedido);
        pedidoCreacion.setCantidad(cantidad);

        PedidoCreacion guardado = pedidoCreacionRepository.save(pedidoCreacion);

        pedido.getCreacionesPedido().add(guardado); //Guardamos la creacion en el pedido correspondiente

        return guardado;
    }
}
