package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Pedido;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    Pedido findById(long id);
    List<Pedido> findByFechaBetween(LocalDateTime inicioDelDia, LocalDateTime finDelDia);
    List<Pedido> findAllByEstaPago(boolean estaPago);
}
