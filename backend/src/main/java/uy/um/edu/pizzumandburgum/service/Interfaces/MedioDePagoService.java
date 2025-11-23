package uy.um.edu.pizzumandburgum.service.Interfaces;

import uy.um.edu.pizzumandburgum.dto.request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.update.MedioDePagoUpdateDTO;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;

import java.util.List;

public interface MedioDePagoService {
    MedioDePago obtenerMedioDePago (String email,Long id);
    MedioDePagoDTO aniadirMedioDePago(MedioDePagoRequestDTO dto, String idCliente);
    List<MedioDePagoDTO> listarPorCliente(String email);
    void eliminarMedioDePago(String email, Long id);
    MedioDePagoDTO editarMDP(Long id, MedioDePagoUpdateDTO dto);
}
