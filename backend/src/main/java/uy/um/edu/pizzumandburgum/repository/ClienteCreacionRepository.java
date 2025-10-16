package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Cliente;

public interface ClienteCreacionRepository extends JpaRepository<Cliente,String> {
}
