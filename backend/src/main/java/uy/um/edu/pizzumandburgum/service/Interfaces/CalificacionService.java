package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.CalificacionRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.CalificacionResponseDTO;

public interface CalificacionService {
    CalificacionResponseDTO crearCalificacion(CalificacionRequestDTO dto);
    CalificacionResponseDTO calificacionPorPedido(Long idPedido);
}
