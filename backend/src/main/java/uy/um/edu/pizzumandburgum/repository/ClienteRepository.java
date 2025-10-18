package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Cliente;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,String> {
    Cliente findByEmail(String email);
    boolean existsByEmail(String email);
}
