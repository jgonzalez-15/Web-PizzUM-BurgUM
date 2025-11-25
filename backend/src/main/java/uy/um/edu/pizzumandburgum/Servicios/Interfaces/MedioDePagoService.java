package uy.um.edu.pizzumandburgum.Servicios.Interfaces;

import uy.um.edu.pizzumandburgum.DTOs.Request.MedioDePagoRequestDTO;
import uy.um.edu.pizzumandburgum.DTOs.Response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.DTOs.Update.MedioDePagoUpdateDTO;
import uy.um.edu.pizzumandburgum.Entidades.MedioDePago;

import java.util.List;

public interface MedioDePagoService {
    MedioDePago obtenerMedioDePago (String email,Long id);
    MedioDePagoDTO aniadirMedioDePago(MedioDePagoRequestDTO dto, String idCliente);
    List<MedioDePagoDTO> listarPorCliente(String email);
    void eliminarMedioDePago(String email, Long id);
    MedioDePagoDTO editarMDP(Long id, MedioDePagoUpdateDTO dto);
}
