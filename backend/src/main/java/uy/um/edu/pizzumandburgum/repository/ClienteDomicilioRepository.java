package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Administrador;
import uy.um.edu.pizzumandburgum.entities.ClienteDomicilio;

public interface ClienteDomicilioRepository extends JpaRepository<ClienteDomicilio,Long> {

}
