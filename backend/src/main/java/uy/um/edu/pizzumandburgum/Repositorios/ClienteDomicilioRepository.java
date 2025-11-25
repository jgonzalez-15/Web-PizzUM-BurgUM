package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.ClienteDomicilio;

import java.util.Optional;

public interface ClienteDomicilioRepository extends JpaRepository<ClienteDomicilio,Long> {
    boolean existsByCliente_EmailAndDomicilio_Id(String clienteEmail, Long domicilioId);
    Optional<ClienteDomicilio> findByCliente_EmailAndDomicilio_Id(String clienteEmail, Long domicilioId);
    long countByCliente_Email(String clienteEmail);

}
