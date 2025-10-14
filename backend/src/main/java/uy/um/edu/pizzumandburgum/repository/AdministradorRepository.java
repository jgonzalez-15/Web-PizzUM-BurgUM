package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Administrador;



public interface AdministradorRepository extends JpaRepository <Administrador,String> {
    Administrador findByEmail(String email);
    boolean existsByEmail(String email);
}
