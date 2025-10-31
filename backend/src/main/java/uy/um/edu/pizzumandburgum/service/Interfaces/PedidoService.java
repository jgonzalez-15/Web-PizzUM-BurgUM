package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;

import java.util.List;

public interface PedidoService {
    PedidoResponseDTO realizarPedido(PedidoRequestDTO dto);
    void eliminarPedido(Long id);
    String consultarEstado(Long id);
    void cambiarEstado(Long id);
    List<PedidoResponseDTO> pedidosEnCurso();
}
