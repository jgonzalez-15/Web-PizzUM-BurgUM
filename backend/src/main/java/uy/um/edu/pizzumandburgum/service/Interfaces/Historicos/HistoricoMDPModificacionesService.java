package uy.um.edu.pizzumandburgum.service.Interfaces.Historicos;

import uy.um.edu.pizzumandburgum.entities.Domicilio;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;

public interface HistoricoMDPModificacionesService {
    void registrarActualizacion(MedioDePago anterior,MedioDePago nuevo);
    void RegistrarAgregar(MedioDePago nuevo);
    void RegistrarEliminar(MedioDePago viejo);
}
