package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;

public interface PedidoService {
    PedidoResponseDTO realizarPedido(String email,String direccion,Long idPedido, Long numero);
    void eliminarPedido(Long id);
    String consultarEstado(Long id);
    void cambiarEstado(Long id, String estado);
}
