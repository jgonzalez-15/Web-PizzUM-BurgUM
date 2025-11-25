package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uy.um.edu.pizzumandburgum.Entidades.Pizza;

import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, String> {
    Optional<Pizza> findById(Long id);
    @Query("SELECT p FROM Pizza p LEFT JOIN FETCH p.ingredientes WHERE p.id = :idPizza")
    Optional<Pizza> findByIdConIngredientes(@Param("idPizza") Long idPizza);
}
