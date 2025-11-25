package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uy.um.edu.pizzumandburgum.Entidades.Domicilio;

import java.util.Optional;

public interface DomicilioRepository extends JpaRepository<Domicilio,Long> {
    Optional<Domicilio>findById(Long domicilio);
    @Query("SELECT COUNT(cd) FROM ClienteDomicilio cd WHERE cd.cliente.email = :clienteId")
    long countByClienteId(@Param("clienteId") String clienteId);

}
