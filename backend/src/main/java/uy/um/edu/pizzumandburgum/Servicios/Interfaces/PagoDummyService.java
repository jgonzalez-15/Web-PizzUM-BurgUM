package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.PagoDummyRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.PagoDummyResponseDTO;

public interface PagoDummyService {
    PagoDummyResponseDTO procesarPago(PagoDummyRequestDTO request);
}
