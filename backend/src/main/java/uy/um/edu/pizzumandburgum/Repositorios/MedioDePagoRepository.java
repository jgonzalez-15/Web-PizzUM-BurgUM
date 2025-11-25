package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.MedioDePago;

import java.util.List;
import java.util.Optional;

public interface MedioDePagoRepository extends JpaRepository<MedioDePago, Long> {
    Optional<MedioDePago> findByClienteEmailAndId(String email, Long id);
    Optional<MedioDePago> findById(Long id);
    List<MedioDePago> findByClienteEmailAndEstaActivoTrue(String email);
    Optional<MedioDePago> findByNumeroTarjeta(Long numeroTarjeta);
}
