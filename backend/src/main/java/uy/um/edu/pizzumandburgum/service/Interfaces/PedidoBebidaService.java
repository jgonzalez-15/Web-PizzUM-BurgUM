package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.entities.PedidoBebida;

public interface PedidoBebidaService {
    PedidoBebida agregarBebida (Long pedidoId, Long bebidaId, int cantidad);
}
