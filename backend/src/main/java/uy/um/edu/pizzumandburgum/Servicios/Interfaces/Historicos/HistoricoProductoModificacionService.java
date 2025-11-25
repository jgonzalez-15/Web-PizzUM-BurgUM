package uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos;

import uy.um.edu.pizzumandburgum.DTOs.Response.Historicos.HistoricoProductoResponseDTO;
import uy.um.edu.pizzumandburgum.Entidades.Producto;

import java.util.List;

public interface HistoricoProductoModificacionService {
    void registrarActualizacion(Producto anterior, Producto nuevo);
    void RegistrarAgregar(Producto nuevo);
    void RegistrarEliminar(Producto viejo);
    void RegistrarOculto(Producto pAnterior);
    List<HistoricoProductoResponseDTO> listarHistoricosPorProducto(Long productoId);
    List<HistoricoProductoResponseDTO> listarTodosLosHistoricos();
    List<HistoricoProductoResponseDTO> listarHistoricosPorTipo(String tipoModificacion);

}
