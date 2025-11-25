package uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos;

import uy.um.edu.pizzumandburgum.Entidades.MedioDePago;

public interface HistoricoMDPModificacionesService {
    void registrarActualizacion(MedioDePago anterior,MedioDePago nuevo);
    void RegistrarAgregar(MedioDePago nuevo);
    void RegistrarEliminar(MedioDePago viejo);
}
