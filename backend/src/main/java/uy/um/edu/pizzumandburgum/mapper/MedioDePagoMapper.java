package uy.um.edu.pizzumandburgum.mapper;

import org.springframework.stereotype.Component;
import uy.um.edu.pizzumandburgum.dto.response.MedioDePagoDTO;
import uy.um.edu.pizzumandburgum.dto.response.PedidoBebidaResponseDTO;
import uy.um.edu.pizzumandburgum.entities.MedioDePago;
import uy.um.edu.pizzumandburgum.entities.PedidoBebida;

@Component
public class MedioDePagoMapper {
    public MedioDePago toEntity(MedioDePagoDTO dto) {
        MedioDePago medioDePago = new MedioDePago();
        medioDePago.setCliente(dto.getCliente());
        medioDePago.setDireccion(dto.getDireccion());
        medioDePago.setNumero(dto.getNumero());
        medioDePago.setVencimiento(dto.getVencimiento());

        return medioDePago;
    }

    public MedioDePagoDTO toResponseDTO(MedioDePago medioDePago) {
        return new MedioDePagoDTO(medioDePago.getNumero(),medioDePago.getVencimiento(),medioDePago.getDireccion(),medioDePago.getCliente());
    }
}
