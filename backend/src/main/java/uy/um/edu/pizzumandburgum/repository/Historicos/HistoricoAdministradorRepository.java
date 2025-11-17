package uy.um.edu.pizzumandburgum.repository.Historicos;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoAdministradorModificaciones;


import java.util.List;

public interface HistoricoAdministradorRepository extends JpaRepository<HistoricoAdministradorModificaciones,Long> {
    List<HistoricoAdministradorModificaciones> findByAdministradorOrderByFechaModificacionDesc(Administrador administrador);

    List<HistoricoAdministradorModificaciones> findAllByOrderByFechaModificacionDesc();

    List<HistoricoAdministradorModificaciones> findByTipoModificacionOrderByFechaModificacionDesc(String tipoModificacion);
}
