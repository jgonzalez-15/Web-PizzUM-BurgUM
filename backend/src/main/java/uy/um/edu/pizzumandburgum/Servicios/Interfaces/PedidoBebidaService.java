package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.Entidades.PedidoBebida;

public interface PedidoBebidaService {
    PedidoBebida agregarBebida (Long pedidoId, Long bebidaId, int cantidad);
}
