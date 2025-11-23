package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Administrador;

import java.util.Optional;


public interface AdministradorRepository extends JpaRepository <Administrador,String> {
    Optional<Administrador> findByEmail(String email);
}
