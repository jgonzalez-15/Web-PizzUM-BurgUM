package uy.um.edu.pizzumandburgum.Repositorios.Historicos;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Administrador;
import uy.um.edu.pizzumandburgum.Entidades.Historicos.HistoricoAdministradorModificaciones;


import java.util.List;

public interface HistoricoAdministradorRepository extends JpaRepository<HistoricoAdministradorModificaciones,Long> {
    List<HistoricoAdministradorModificaciones> findByAdministradorOrderByFechaModificacionDesc(Administrador administrador);
    List<HistoricoAdministradorModificaciones> findAllByOrderByFechaModificacionDesc();
    List<HistoricoAdministradorModificaciones> findByTipoModificacionOrderByFechaModificacionDesc(String tipoModificacion);
}
