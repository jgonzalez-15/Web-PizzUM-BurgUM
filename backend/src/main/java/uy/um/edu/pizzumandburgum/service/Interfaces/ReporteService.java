package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.TicketResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    List<AdministradorResponseDTO> obtenerCantidadUsuarios();
    ClienteResponseDTO obtenerClientePorTarjeta(Long numeroTarjeta);
    List<TicketResponseDTO> obtenerTicketsDeVenta(LocalDate fecha);
}
