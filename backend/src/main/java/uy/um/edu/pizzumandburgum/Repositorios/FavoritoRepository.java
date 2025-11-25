package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito,Long> {
    boolean existsByCliente_EmailAndCreacion_Id(String clienteId, Long creacionId);
    void deleteById(Long id);
}
