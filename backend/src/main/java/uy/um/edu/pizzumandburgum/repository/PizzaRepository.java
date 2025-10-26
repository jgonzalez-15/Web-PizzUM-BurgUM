package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Pizza;

import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, String> {
    public Optional<Pizza> findById(Long id);

}
