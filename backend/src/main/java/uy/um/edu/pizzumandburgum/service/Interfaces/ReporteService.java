package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    List<AdministradorResponseDTO> obtenerCantidadUsuarios();
    List<MedioDePagoDTO> obtenerDatosTarjetas();
    List<PedidoResponseDTO> obtenerTicketsDeVenta(LocalDate fecha);
}
