package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;

public interface PedidoService {
    PedidoResponseDTO realizarPedido(String email,String domicilio,PedidoRequestDTO pedido, Long numero);
}
