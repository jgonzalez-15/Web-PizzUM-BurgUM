package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Producto;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository <Producto,Long> {
    Optional <Producto> findByIdProducto(Long Id);
    Optional<Producto> findByNombre(String nombre);

}
