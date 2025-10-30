package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;

import java.util.Optional;

public interface HamburguesaRepository extends JpaRepository<Hamburguesa,Long> {
    @Query("SELECT h FROM Hamburguesa h LEFT JOIN FETCH h.ingredientes WHERE h.id = :id")
    Optional<Hamburguesa> findByIdWithIngredientes(@Param("id") Long id);
}
