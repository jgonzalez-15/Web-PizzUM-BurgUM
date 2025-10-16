package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
    Pedido findById(long id);
}
