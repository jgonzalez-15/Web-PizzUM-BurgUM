package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Creacion;

import java.util.Optional;

public interface CreacionRepository extends JpaRepository<Creacion,String> {
    Optional<Creacion>findById(Long id);

}
