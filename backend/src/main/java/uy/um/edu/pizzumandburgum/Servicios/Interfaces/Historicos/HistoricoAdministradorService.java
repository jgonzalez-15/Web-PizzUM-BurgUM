package uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos;

import uy.um.edu.pizzumandburgum.DTOs.Response.Historicos.HistoricoAdministradorDTO;
import uy.um.edu.pizzumandburgum.Entidades.Administrador;

import java.util.List;

public interface HistoricoAdministradorService {
    void registrarActualizacion(Administrador anterior, Administrador nuevo);
    void RegistrarAgregar(Administrador nuevo);
    void RegistrarEliminar(Administrador aViejo);
    List<HistoricoAdministradorDTO> listarHistoricosPorAdministrador(String emailAdministrador);
    List<HistoricoAdministradorDTO> listarTodosLosHistoricos();
    List<HistoricoAdministradorDTO> listarHistoricosPorTipo(String tipoModificacion);
}
