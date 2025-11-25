package uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos;

import uy.um.edu.pizzumandburgum.DTOs.Response.Historicos.HistoricoClienteResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;

import java.util.List;

public interface HistoricoClienteModificacionesService {
    void registrarActualizacion(Cliente anterior, Cliente nuevo);
    void RegistrarAgregar(Cliente nuevo);
    List<HistoricoClienteResponseDTO> listarHistoricosPorCliente(String emailCliente);
    List<HistoricoClienteResponseDTO> listarTodosLosHistoricos();
    List<HistoricoClienteResponseDTO> listarHistoricosPorTipo(String tipoModificacion);
}
