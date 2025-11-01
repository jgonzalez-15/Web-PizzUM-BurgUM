package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Calificacion;

import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion,Long> {
    Optional<Calificacion> findByPedidoId(Long pedidoId);
}
