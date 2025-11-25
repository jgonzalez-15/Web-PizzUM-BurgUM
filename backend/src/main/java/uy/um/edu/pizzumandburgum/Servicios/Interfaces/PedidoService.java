package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.PedidoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.PedidoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface PedidoService {
    PedidoResponseDTO realizarPedido(PedidoRequestDTO dto);
    void eliminarPedido(Long id);
    String consultarEstado(Long id);
    void cambiarEstado(Long id);
    List<PedidoResponseDTO> pedidosEnCurso();
    List<PedidoResponseDTO> listarPedidosPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);
    PedidoResponseDTO obtenerPedidoPorId(Long id);
    void calificar(Long id, int calificacion);
}
