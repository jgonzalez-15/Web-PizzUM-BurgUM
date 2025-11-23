package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;

import java.util.List;
import java.util.Optional;

public interface MedioDePagoRepository extends JpaRepository<MedioDePago, Long> {
    Optional<MedioDePago> findByClienteEmailAndId(String email, Long id);
    Optional<MedioDePago> findById(Long id);
    List<MedioDePago> findByClienteEmailAndEstaActivoTrue(String email);

}
