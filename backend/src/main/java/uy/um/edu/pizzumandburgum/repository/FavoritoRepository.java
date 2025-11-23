package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito,Long> {
    boolean existsByCliente_EmailAndCreacion_Id(String clienteId, Long creacionId);
    void deleteById(Long id);
}
