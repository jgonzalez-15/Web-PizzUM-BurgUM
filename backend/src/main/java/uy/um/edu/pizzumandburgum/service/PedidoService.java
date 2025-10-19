package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;
import uy.um.edu.pizzumandburgum.entities.Pedido;

import java.util.List;

public interface PedidoService {
    PedidoResponseDTO realizarPedido(String email,String domicilio,PedidoRequestDTO pedido, Long numero);
    void eliminarPedido(Long id);
    String consultarEstado(Long id);

}
