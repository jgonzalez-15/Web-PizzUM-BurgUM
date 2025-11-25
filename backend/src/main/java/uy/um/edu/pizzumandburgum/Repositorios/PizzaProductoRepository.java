package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.PizzaProducto;

public interface PizzaProductoRepository extends JpaRepository<PizzaProducto,Long> {
}
