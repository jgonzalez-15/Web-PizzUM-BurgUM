package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Response.AdministradorResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.ClienteResponseDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.TicketResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    List<AdministradorResponseDTO> obtenerCantidadUsuarios();
    ClienteResponseDTO obtenerClientePorTarjeta(Long numeroTarjeta);
    List<TicketResponseDTO> obtenerTicketsDeVenta(LocalDate fecha);
}
