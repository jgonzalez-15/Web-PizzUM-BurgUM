package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Producto;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository <Producto,Long> {
    Optional <Producto> findByIdProducto(Long Id);
    Optional<Producto> findByNombre(String nombre);

}
