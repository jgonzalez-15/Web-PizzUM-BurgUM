package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Domicilio;

import java.util.List;
import java.util.Optional;

public interface DomicilioRepository extends JpaRepository<Domicilio,String> {
    Optional<Domicilio>findById(Long Domicilio);
    long countByClienteId(String clienteId);
    List<Domicilio> findByClienteIdAndIdNot(Long clienteId, Long domicilioId);
}
