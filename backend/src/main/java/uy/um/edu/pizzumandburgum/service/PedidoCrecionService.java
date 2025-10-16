package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.entities.PedidoCreacion;

public interface PedidoCrecionService {
    PedidoCreacion agregarCreacion(Long pedidoId, Long creacionId, int cantidad);
}
