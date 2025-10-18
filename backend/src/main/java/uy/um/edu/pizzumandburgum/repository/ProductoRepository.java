package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Producto;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository <Producto,Long> {
    Producto findById(long Id);
    Optional<Object> findByNombre(String nombre);
}
