package uy.um.edu.pizzumandburgum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.entities.Hamburguesa;

public interface HamburguesaRepository extends JpaRepository<Hamburguesa,Long> {
}
