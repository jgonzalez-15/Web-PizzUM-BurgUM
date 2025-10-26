package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Cliente;
import uy.um.edu.pizzumandburgum.entities.Creacion;

import java.util.Optional;

public interface CreacionRepository extends JpaRepository<Creacion,String> {
    Optional<Creacion>findById(Long id);

}
