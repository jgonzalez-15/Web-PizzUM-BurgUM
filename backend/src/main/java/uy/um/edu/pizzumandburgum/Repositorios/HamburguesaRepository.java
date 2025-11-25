package uy.um.edu.pizzumandburgum.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.um.edu.pizzumandburgum.Entidades.Hamburguesa;

public interface HamburguesaRepository extends JpaRepository<Hamburguesa,Long> {
}
