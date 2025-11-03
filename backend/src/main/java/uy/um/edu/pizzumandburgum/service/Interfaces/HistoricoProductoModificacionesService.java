package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.ProductoRequestDTO;
import uy.um.edu.pizzumandburgum.entities.Producto;

public interface HistoricoProductoModificacionesService {
    void registrarActualizacion(Producto anterior, Producto nuevo);
    void RegistrarAgregar(Producto nuevo);
    void RegistrarEliminar(Producto viejo);

}
