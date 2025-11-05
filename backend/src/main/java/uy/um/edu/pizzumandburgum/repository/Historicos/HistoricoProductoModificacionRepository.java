package uy.um.edu.pizzumandburgum.repository.Historicos;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Historicos.HistoricoProductoModificacion;
import uy.um.edu.pizzumandburgum.entities.Producto;

import java.util.List;

public interface HistoricoProductoModificacionRepository extends JpaRepository<HistoricoProductoModificacion,Long> {
    List<HistoricoProductoModificacion> findByProductoOrderByFechaModificacionDesc(Producto producto);

    List<HistoricoProductoModificacion> findAllByOrderByFechaModificacionDesc();

    List<HistoricoProductoModificacion> findByTipoModificiacionOrderByFechaModificacionDesc(String tipoModificiacion);
}
