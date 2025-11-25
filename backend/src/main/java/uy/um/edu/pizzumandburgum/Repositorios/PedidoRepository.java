package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Pedido;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    List<Pedido> findByFechaBetween(LocalDate inicioDelDia, LocalDate finDelDia);
}
