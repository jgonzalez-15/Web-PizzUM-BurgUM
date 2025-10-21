package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.entities.MedioDePago;

public interface MedioDePagoService {
    MedioDePago obtenerMedioDePago (String email,Long numero);
}
