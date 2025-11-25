package uy.um.edu.pizzumandburgum.Repositorios.Historicos;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Historicos.HistoricoProductoModificacion;
import uy.um.edu.pizzumandburgum.Entidades.Producto;

import java.util.List;

public interface HistoricoProductoModificacionRepository extends JpaRepository<HistoricoProductoModificacion,Long> {
    List<HistoricoProductoModificacion> findByProductoOrderByFechaModificacionDesc(Producto producto);

    List<HistoricoProductoModificacion> findAllByOrderByFechaModificacionDesc();

    List<HistoricoProductoModificacion> findByTipoModificiacionOrderByFechaModificacionDesc(String tipoModificiacion);
}
