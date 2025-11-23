package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.TicketResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    List<AdministradorResponseDTO> obtenerCantidadUsuarios();
    List<MedioDePagoDTO> obtenerDatosTarjetas();
    List<TicketResponseDTO> obtenerTicketsDeVenta(LocalDate fecha);
}
