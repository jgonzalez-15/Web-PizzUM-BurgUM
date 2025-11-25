package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.Entidades.PedidoCreacion;

public interface PedidoCrecionService {
    PedidoCreacion agregarCreacion(Long pedidoId, Long creacionId, int cantidad);
}
