package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Cliente;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,String> {
    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);
}
