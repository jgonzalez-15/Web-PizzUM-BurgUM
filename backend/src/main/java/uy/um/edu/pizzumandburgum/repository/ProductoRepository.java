package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Producto;

public interface ProductoRepository extends JpaRepository <Producto,Long> {
}
