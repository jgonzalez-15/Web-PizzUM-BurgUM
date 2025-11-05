package uy.um.edu.pizzumandburgum.repository.Historicos;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoClienteModificaciones;

import java.util.List;

public interface HistoricoClienteModificacionesRepository extends JpaRepository<HistoricoClienteModificaciones,Long> {
    List<HistoricoClienteModificaciones> findByClienteOrderByFechaModificacionDesc(Cliente cliente);

    List<HistoricoClienteModificaciones> findAllByOrderByFechaModificacionDesc();

    List<HistoricoClienteModificaciones> findByTipoModificacionOrderByFechaModificacionDesc(String tipoModificacion);
}
