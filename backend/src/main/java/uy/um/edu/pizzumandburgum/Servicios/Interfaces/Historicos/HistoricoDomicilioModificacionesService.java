package uy.um.edu.pizzumandburgum.Servicios.Interfaces.Historicos;

import uy.um.edu.pizzumandburgum.Entidades.Domicilio;

public interface HistoricoDomicilioModificacionesService {
    void registrarActualizacion(Domicilio anterior, Domicilio nuevo);
    void RegistrarAgregar(Domicilio nuevo);
    void RegistrarEliminar(Domicilio viejo);
}
