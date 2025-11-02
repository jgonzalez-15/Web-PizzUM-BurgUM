package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.PizzaProducto;

public interface PizzaProductoRepository extends JpaRepository<PizzaProducto,Long> {
}
