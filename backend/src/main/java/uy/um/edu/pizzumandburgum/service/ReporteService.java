package uy.um.edu.pizzumandburgum.service;

import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    Long obtenerCantidadUsuarios();
    List<MedioDePagoDTO> obtenerDatosTarjetas();
    List<PedidoResponseDTO> obtenerTicketsDeVenta(LocalDate inicio, LocalDate fin);
}
