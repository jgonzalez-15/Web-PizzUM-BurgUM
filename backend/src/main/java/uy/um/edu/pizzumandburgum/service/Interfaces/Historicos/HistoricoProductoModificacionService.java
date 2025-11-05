package uy.um.edu.pizzumandburgum.service.Interfaces.Historicos;

import uy.um.edu.pizzumandburgum.entities.Producto;

public interface HistoricoProductoModificacionService {
    void registrarActualizacion(Producto anterior, Producto nuevo);
    void RegistrarAgregar(Producto nuevo);
    void RegistrarEliminar(Producto viejo);

}
