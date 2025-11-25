package uy.um.edu.pizzumandburgum.Repositorios.Historicos;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;
import uy.um.edu.pizzumandburgum.Entidades.Historicos.HistoricoClienteModificaciones;

import java.util.List;

public interface HistoricoClienteModificacionesRepository extends JpaRepository<HistoricoClienteModificaciones,Long> {
    List<HistoricoClienteModificaciones> findByClienteOrderByFechaModificacionDesc(Cliente cliente);
    List<HistoricoClienteModificaciones> findAllByOrderByFechaModificacionDesc();
    List<HistoricoClienteModificaciones> findByTipoModificacionOrderByFechaModificacionDesc(String tipoModificacion);
}
