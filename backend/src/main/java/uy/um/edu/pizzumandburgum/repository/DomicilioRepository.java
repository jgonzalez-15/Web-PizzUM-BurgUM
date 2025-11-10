package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

import java.util.List;
import java.util.Optional;

public interface DomicilioRepository extends JpaRepository<Domicilio,Long> {
    Optional<Domicilio>findById(Long domicilio);
    @Query("SELECT COUNT(cd) FROM ClienteDomicilio cd WHERE cd.cliente.email = :clienteId")
    long countByClienteId(@Param("clienteId") String clienteId);

}
